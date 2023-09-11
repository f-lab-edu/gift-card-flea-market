package com.ghm.giftcardfleamarket.purchase.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PageSize.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.exception.ItemNotFoundException;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;
import com.ghm.giftcardfleamarket.purchase.domain.Purchase;
import com.ghm.giftcardfleamarket.purchase.dto.request.PurchaseRequest;
import com.ghm.giftcardfleamarket.purchase.dto.response.AvailablePurchaseDetailResponse;
import com.ghm.giftcardfleamarket.purchase.dto.response.AvailablePurchaseListResponse;
import com.ghm.giftcardfleamarket.purchase.dto.response.AvailablePurchaseResponse;
import com.ghm.giftcardfleamarket.purchase.exception.PurchaseGiftCardNotFoundException;
import com.ghm.giftcardfleamarket.purchase.mapper.PurchaseMapper;
import com.ghm.giftcardfleamarket.sale.domain.Sale;
import com.ghm.giftcardfleamarket.sale.exception.SaleGiftCardNotFoundException;
import com.ghm.giftcardfleamarket.sale.mapper.SaleMapper;
import com.ghm.giftcardfleamarket.user.exception.UnauthorizedUserException;
import com.ghm.giftcardfleamarket.user.mapper.UserMapper;
import com.ghm.giftcardfleamarket.user.service.LoginService;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final PurchaseMapper purchaseMapper;
	private final SaleMapper saleMapper;
	private final UserMapper userMapper;
	private final BrandMapper brandMapper;
	private final ItemMapper itemMapper;
	private final LoginService loginService;

	@Getter
	@AllArgsConstructor
	private static class ItemBrandPair {
		private Item item;
		private String brandName;
	}

	@Transactional
	public void buyGiftCard(PurchaseRequest purchaseRequest) {
		Purchase purchase = purchaseRequest.toEntity(findLoginUserIdInSession());
		purchaseMapper.insertPurchaseGiftCard(purchase);

		Map<String, Object> saleIdAndPurchaseStatus = Map.ofEntries(
			Map.entry("saleId", purchase.getSaleId()),
			Map.entry("purchaseStatus", 1));
		saleMapper.updatePurchaseStatus(saleIdAndPurchaseStatus);
	}

	public AvailablePurchaseListResponse getMyAvailableGiftCards(int page) {
		Map<String, Object> userIdAndPageInfo = makePagingQueryParamsWithMap(findLoginUserIdInSession(), page,
			PURCHASE.getValue());
		List<Purchase> purchaseList = purchaseMapper.selectMyAvailableGiftCards(userIdAndPageInfo);

		if (CollectionUtils.isEmpty(purchaseList)) {
			return AvailablePurchaseListResponse.empty();
		}

		List<AvailablePurchaseResponse> availablePurchaseResponseList = purchaseList.stream()
			.map(this::makeAvailablePurchaseResponse)
			.toList();

		return new AvailablePurchaseListResponse(availablePurchaseResponseList);
	}

	public AvailablePurchaseDetailResponse getMyAvailableGiftCardDetails(Long purchaseId) {
		Map<String, Object> userIdAndPurchaseId = Map.ofEntries(
			Map.entry("userId", findLoginUserIdInSession()),
			Map.entry("purchaseId", purchaseId));

		return purchaseMapper.selectMyAvailableGiftCardDetails(userIdAndPurchaseId)
			.map(this::makeAvailablePurchaseDetailResponse)
			.orElseThrow(() -> new PurchaseGiftCardNotFoundException(purchaseId));
	}
	
	public void confirmGiftCardUsage(Long purchaseId) {
		checkMyAvailablePurchaseInfo(purchaseId);
		purchaseMapper.updateUseStatus(purchaseId);
	}

	private void checkMyAvailablePurchaseInfo(Long purchaseId) {
		Map<String, Object> userIdAndPurchaseId = Map.ofEntries(
			Map.entry("userId", findLoginUserIdInSession()),
			Map.entry("purchaseId", purchaseId));

		if (!purchaseMapper.hasMyAvailablePurchaseInfo(userIdAndPurchaseId)) {
			throw new PurchaseGiftCardNotFoundException(purchaseId);
		}
	}

	private AvailablePurchaseDetailResponse makeAvailablePurchaseDetailResponse(Purchase purchase) {
		ItemBrandPair pair = getItemAndBrandName(purchase);
		Item item = pair.getItem();
		Long saleId = purchase.getSaleId();

		Sale sale = saleMapper.selectSaleGiftCardDetails(saleId)
			.orElseThrow(() -> new SaleGiftCardNotFoundException(saleId));

		return AvailablePurchaseDetailResponse.of(purchase, pair.getBrandName(), item.getName(), sale.getBarcode(),
			sale.getExpirationDate());
	}

	private AvailablePurchaseResponse makeAvailablePurchaseResponse(Purchase purchase) {
		ItemBrandPair pair = getItemAndBrandName(purchase);
		Item item = pair.getItem();
		Long saleId = purchase.getSaleId();

		LocalDate expirationDate = saleMapper.selectSaleGiftCardDetails(saleId)
			.map(Sale::getExpirationDate)
			.orElseThrow(() -> new SaleGiftCardNotFoundException(saleId));

		return AvailablePurchaseResponse.of(purchase, pair.getBrandName(), item.getName(), item.getPrice(),
			expirationDate);
	}

	private ItemBrandPair getItemAndBrandName(Purchase purchase) {
		Long itemId = purchase.getItemId();

		Item item = itemMapper.selectItemDetails(itemId)
			.orElseThrow(() -> new ItemNotFoundException(itemId));

		String brandName = brandMapper.selectBrandName(item.getBrandId());

		return new ItemBrandPair(item, brandName);
	}

	private String findLoginUserIdInSession() {
		return loginService.getLoginUser()
			.map(userMapper::selectUserIdById)
			.orElseThrow(() -> new UnauthorizedUserException("로그인 후 이용 가능합니다."));
	}
}