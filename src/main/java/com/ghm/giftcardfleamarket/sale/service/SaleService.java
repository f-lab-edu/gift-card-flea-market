package com.ghm.giftcardfleamarket.sale.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.PriceCalculationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PageSize.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PriceRate.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.common.BaseService;
import com.ghm.giftcardfleamarket.common.ItemBrandPair;
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
import com.ghm.giftcardfleamarket.user.mapper.UserMapper;
import com.ghm.giftcardfleamarket.user.service.LoginService;

@Service
public class SaleService extends BaseService {

	private final SaleMapper saleMapper;

	@Autowired
	public SaleService(ItemMapper itemMapper, BrandMapper brandMapper, LoginService loginService, UserMapper userMapper,
		SaleMapper saleMapper) {
		super(itemMapper, brandMapper, loginService, userMapper);
		this.saleMapper = saleMapper;
	}

	public void sellGiftCard(SaleRequest saleRequest) {
		if (saleMapper.hasBarcode(saleRequest.getBarcode())) {
			throw new DuplicatedBarcodeException("이미 등록된 바코드입니다.");
		}
		saleMapper.insertSaleGiftCard(saleRequest.toEntity(findLoginUserIdInSession()));
	}

	public SaleListResponse getMySoldGiftCards(int page) {
		Map<String, Object> userIdAndPageInfo = makePagingQueryParamsWithMap(findLoginUserIdInSession(), page, SALE);
		List<Sale> saleList = saleMapper.selectMySoldGiftCards(userIdAndPageInfo);

		if (CollectionUtils.isEmpty(saleList)) {
			return SaleListResponse.empty();
		}

		List<SaleResponse> saleResponseList = saleList.stream()
			.map(sale -> {
				Item item = itemMapper.selectItemDetails(sale.getItemId())
					.orElseThrow(() -> new ItemNotFoundException(sale.getItemId()));
				return SaleResponse.of(sale, item.getName(), calculatePrice(item.getPrice(), PROPOSAL));
			})
			.toList();

		return new SaleListResponse(saleResponseList);
	}

	public InventoryListResponse getGiftCardInventoriesByExpirationDate(Long itemId) {
		ItemBrandPair pair = getItemAndBrandName(itemId);
		List<Inventory> inventoryList = saleMapper.selectGiftCardInventoriesByExpirationDate(itemId);

		if (CollectionUtils.isEmpty(inventoryList)) {
			throw new GiftCardInventoryNotFoundException("찾는 상품의 기프티콘 재고가 없습니다.");
		}

		List<InventoryResponse> inventoryResponseList = inventoryList.stream()
			.map(inventory -> makeInventoryResponse(pair.getItem(), pair.getBrandName(), inventory))
			.toList();

		return new InventoryListResponse(inventoryResponseList);
	}

	private InventoryResponse makeInventoryResponse(Item item, String brandName, Inventory inventory) {
		LocalDate expirationDate = inventory.getExpirationDate();
		long daysBetween = LocalDate.now().until(expirationDate, ChronoUnit.DAYS);

		int salePrice = (daysBetween >= 0 && daysBetween <= 7) ?
			calculatePrice(item.getPrice(), HIGH_DISCOUNT) :
			calculatePrice(item.getPrice(), STANDARD_DISCOUNT);

		return InventoryResponse.of(inventory, brandName, item.getName(), salePrice);
	}
}