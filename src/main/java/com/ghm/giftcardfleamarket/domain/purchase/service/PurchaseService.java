package com.ghm.giftcardfleamarket.domain.purchase.service;

import static com.ghm.giftcardfleamarket.global.util.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.global.util.constants.PageSize.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ghm.giftcardfleamarket.domain.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.domain.item.domain.Item;
import com.ghm.giftcardfleamarket.domain.item.mapper.ItemMapper;
import com.ghm.giftcardfleamarket.domain.purchase.domain.Purchase;
import com.ghm.giftcardfleamarket.domain.purchase.dto.request.PurchaseRequest;
import com.ghm.giftcardfleamarket.domain.purchase.dto.response.AvailablePurchaseDetailResponse;
import com.ghm.giftcardfleamarket.domain.purchase.dto.response.AvailablePurchaseListResponse;
import com.ghm.giftcardfleamarket.domain.purchase.dto.response.AvailablePurchaseResponse;
import com.ghm.giftcardfleamarket.domain.purchase.dto.response.UsedOrExpiredPurchaseListResponse;
import com.ghm.giftcardfleamarket.domain.purchase.dto.response.UsedOrExpiredPurchaseResponse;
import com.ghm.giftcardfleamarket.domain.purchase.exception.PurchaseGiftCardNotFoundException;
import com.ghm.giftcardfleamarket.domain.purchase.mapper.PurchaseMapper;
import com.ghm.giftcardfleamarket.domain.sale.domain.Sale;
import com.ghm.giftcardfleamarket.domain.sale.exception.SaleGiftCardNotFoundException;
import com.ghm.giftcardfleamarket.domain.sale.mapper.SaleMapper;
import com.ghm.giftcardfleamarket.domain.user.mapper.UserMapper;
import com.ghm.giftcardfleamarket.domain.user.service.LoginService;
import com.ghm.giftcardfleamarket.global.common.model.ItemBrandPair;
import com.ghm.giftcardfleamarket.global.common.service.CommonService;

@Service
public class PurchaseService extends CommonService {

	private final PurchaseMapper purchaseMapper;
	private final SaleMapper saleMapper;

	@Autowired
	public PurchaseService(ItemMapper itemMapper, BrandMapper brandMapper, LoginService loginService,
		UserMapper userMapper, PurchaseMapper purchaseMapper, SaleMapper saleMapper) {
		super(itemMapper, brandMapper, loginService, userMapper);
		this.purchaseMapper = purchaseMapper;
		this.saleMapper = saleMapper;
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

	@Transactional(readOnly = true)
	public AvailablePurchaseListResponse getMyAvailableGiftCards(int page) {
		Map<String, Object> userIdAndPageInfo = makePagingQueryParamsWithMap(findLoginUserIdInSession(), page,
			PURCHASE);
		List<Purchase> purchaseList = purchaseMapper.selectMyAvailableGiftCards(userIdAndPageInfo);

		if (CollectionUtils.isEmpty(purchaseList)) {
			return AvailablePurchaseListResponse.empty();
		}

		List<AvailablePurchaseResponse> availablePurchaseResponseList = purchaseList.stream()
			.map(this::makeAvailablePurchaseResponse)
			.toList();

		return new AvailablePurchaseListResponse(availablePurchaseResponseList);
	}

	@Transactional(readOnly = true)
	public UsedOrExpiredPurchaseListResponse getMyUsedOrExpiredGiftCards(int page) {
		Map<String, Object> userIdAndPageInfo = makePagingQueryParamsWithMap(findLoginUserIdInSession(), page,
			PURCHASE);
		List<Purchase> purchaseList = purchaseMapper.selectMyUsedOrExpiredGiftCards(userIdAndPageInfo);

		if (CollectionUtils.isEmpty(purchaseList)) {
			return UsedOrExpiredPurchaseListResponse.empty();
		}

		List<UsedOrExpiredPurchaseResponse> usedOrExpiredPurchaseResponseList = purchaseList.stream()
			.map(this::makeUsedOrExpiredPurchaseResponse)
			.toList();

		return new UsedOrExpiredPurchaseListResponse(usedOrExpiredPurchaseResponseList);
	}

	@Transactional(readOnly = true)
	public AvailablePurchaseDetailResponse getMyAvailableGiftCardDetails(Long purchaseId) {
		checkMyAvailablePurchaseInfo(purchaseId);
		Purchase purchase = purchaseMapper.selectMyAvailableGiftCardDetails(purchaseId);

		return makeAvailablePurchaseDetailResponse(purchase);
	}

	@Transactional
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
		ItemBrandPair pair = getItemAndBrandName(purchase.getItemId());
		Item item = pair.getItem();
		Long saleId = purchase.getSaleId();

		Sale sale = saleMapper.selectSaleGiftCardDetails(saleId)
			.orElseThrow(() -> new SaleGiftCardNotFoundException(saleId));

		return AvailablePurchaseDetailResponse.of(purchase, pair.getBrandName(), item.getName(), sale.getBarcode(),
			sale.getExpirationDate());
	}

	private UsedOrExpiredPurchaseResponse makeUsedOrExpiredPurchaseResponse(Purchase purchase) {
		ItemBrandPair pair = getItemAndBrandName(purchase.getItemId());
		Item item = pair.getItem();
		Long saleId = purchase.getSaleId();

		Sale sale = saleMapper.selectSaleGiftCardDetails(saleId)
			.orElseThrow(() -> new SaleGiftCardNotFoundException(saleId));

		return UsedOrExpiredPurchaseResponse.of(purchase, pair.getBrandName(), item.getName(), item.getPrice(),
			sale.getExpirationDate(), sale.isExpirationStatus());
	}

	private AvailablePurchaseResponse makeAvailablePurchaseResponse(Purchase purchase) {
		ItemBrandPair pair = getItemAndBrandName(purchase.getItemId());
		Item item = pair.getItem();
		Long saleId = purchase.getSaleId();

		LocalDate expirationDate = saleMapper.selectSaleGiftCardDetails(saleId)
			.map(Sale::getExpirationDate)
			.orElseThrow(() -> new SaleGiftCardNotFoundException(saleId));

		return AvailablePurchaseResponse.of(purchase, pair.getBrandName(), item.getName(), item.getPrice(),
			expirationDate);
	}
}