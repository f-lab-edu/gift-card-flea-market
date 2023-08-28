package com.ghm.giftcardfleamarket.sale.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.PriceCalculationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.Page.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PriceRate.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.exception.ItemNotFoundException;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;
import com.ghm.giftcardfleamarket.sale.domain.Sale;
import com.ghm.giftcardfleamarket.sale.dto.Inventory;
import com.ghm.giftcardfleamarket.sale.dto.request.SaleRequest;
import com.ghm.giftcardfleamarket.sale.dto.response.InventoryListResponse;
import com.ghm.giftcardfleamarket.sale.dto.response.InventoryResponse;
import com.ghm.giftcardfleamarket.sale.dto.response.SaleListResponse;
import com.ghm.giftcardfleamarket.sale.dto.response.SaleResponse;
import com.ghm.giftcardfleamarket.sale.exception.DuplicatedBarcodeException;
import com.ghm.giftcardfleamarket.sale.exception.GiftCardInventoryNotFoundException;
import com.ghm.giftcardfleamarket.sale.mapper.SaleMapper;
import com.ghm.giftcardfleamarket.user.exception.UnauthorizedUserException;
import com.ghm.giftcardfleamarket.user.mapper.UserMapper;
import com.ghm.giftcardfleamarket.user.service.LoginService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

	private final SaleMapper saleMapper;
	private final UserMapper userMapper;
	private final BrandMapper brandMapper;
	private final ItemMapper itemMapper;
	private final LoginService loginService;

	public void sellGiftCard(SaleRequest saleRequest) {
		if (saleMapper.hasBarcode(saleRequest.getBarcode())) {
			throw new DuplicatedBarcodeException("이미 등록된 바코드입니다.");
		}
		saleMapper.insertSaleGiftCard(saleRequest.toEntity(findLoginUserIdInSession()));
	}

	public SaleListResponse getMySoldGiftCards(int page) {
		Map<String, Object> userIdAndPageInfo = makePagingQueryParamsWithMap(findLoginUserIdInSession(), page,
			SALE_PAGE_SIZE.getPageSize());
		List<Sale> saleList = saleMapper.selectMySoldGiftCards(userIdAndPageInfo);

		if (CollectionUtils.isEmpty(saleList)) {
			return SaleListResponse.empty();
		}

		List<SaleResponse> saleResponseList = saleList.stream()
			.map(sale -> {
				Item item = itemMapper.selectItemDetails(sale.getItemId())
					.orElseThrow(() -> new ItemNotFoundException(sale.getItemId()));
				return SaleResponse.of(sale, item.getName(), calculatePrice(item.getPrice(), PROPOSAL_RATE.getRate()));
			})
			.toList();

		return new SaleListResponse(saleResponseList);
	}

	public InventoryListResponse getGiftCardInventoriesByExpirationDate(Long itemId) {
		LocalDate currentDate = LocalDate.now();

		Item item = itemMapper.selectItemDetails(itemId)
			.orElseThrow(() -> new ItemNotFoundException(itemId));

		String brandName = brandMapper.selectBrandName(item.getBrandId());
		List<Inventory> inventoryList = saleMapper.selectGiftCardInventoriesByExpirationDate(itemId);

		if (CollectionUtils.isEmpty(inventoryList)) {
			throw new GiftCardInventoryNotFoundException("찾는 상품의 기프티콘 재고가 없습니다.");
		}

		List<InventoryResponse> inventoryResponseList = inventoryList.stream()
			.map(inventory -> {
				LocalDate expirationDate = inventory.getExpirationDate();
				long daysBetween = currentDate.until(expirationDate, ChronoUnit.DAYS);

				int salePrice = (daysBetween >= 0 && daysBetween <= 7) ?
					calculatePrice(item.getPrice(), HIGH_DISCOUNT_RATE.getRate()) :
					calculatePrice(item.getPrice(), DISCOUNT_RATE.getRate());

				return InventoryResponse.of(inventory, brandName, item.getName(), salePrice);
			})
			.toList();

		return new InventoryListResponse(inventoryResponseList);
	}

	private String findLoginUserIdInSession() {
		return loginService.getLoginUser()
			.map(userMapper::selectUserIdById)
			.orElseThrow(() -> new UnauthorizedUserException("로그인 후 이용 가능합니다."));
	}
}