package com.ghm.giftcardfleamarket.item.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.PriceCalculationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.Page.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PriceRate.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.dto.response.ItemDetailResponse;
import com.ghm.giftcardfleamarket.item.dto.response.ItemListResponse;
import com.ghm.giftcardfleamarket.item.dto.response.ItemResponse;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;
import com.ghm.giftcardfleamarket.sale.dto.response.SaleOptionResponse;
import com.ghm.giftcardfleamarket.sale.exception.SaleOptionListNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemMapper itemMapper;
	private final BrandMapper brandMapper;

	public ItemListResponse getItemsByBrand(Long brandId, int page) {
		List<ItemResponse> itemResponseList = new ArrayList<>();

		Map<String, Object> brandIdAndPageInfo = makePagingQueryParamsWithMap(brandId, page,
			ITEM_PAGE_SIZE.getPageSize());
		List<Item> itemList = itemMapper.selectItemsByBrand(brandIdAndPageInfo);
		itemList.forEach(item -> itemResponseList.add(ItemResponse.of(item)));

		return new ItemListResponse(itemMapper.selectItemTotalCountByBrand(brandId), itemResponseList);
	}

	public ItemDetailResponse getItemDetails(Long itemId) {
		Item item = itemMapper.selectItemDetails(itemId);
		String brandName = brandMapper.selectBrandName(item.getBrandId());

		return ItemDetailResponse.of(item, brandName);
	}

	public SaleOptionResponse getItemNamesByBrand(Long brandId) {
		Map<String, Object> brandIdMap = Map.of("id", brandId);
		List<Item> itemList = itemMapper.selectItemsByBrand(brandIdMap);

		if (CollectionUtils.isEmpty(itemList)) {
			throw new SaleOptionListNotFoundException("아이템 목록을 찾을 수 없습니다.");
		}

		return SaleOptionResponse.ofItemList(itemList);
	}

	public SaleOptionResponse getItemProposalPrice(Long itemId) {
		int itemPrice = itemMapper.selectItemDetails(itemId).getPrice();
		return new SaleOptionResponse(calculatePrice(itemPrice, PROPOSAL_RATE.getRate()));
	}
}
