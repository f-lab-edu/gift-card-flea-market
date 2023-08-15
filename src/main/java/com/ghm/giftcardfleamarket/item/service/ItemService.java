package com.ghm.giftcardfleamarket.item.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.Page.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.dto.response.ItemDetailResponse;
import com.ghm.giftcardfleamarket.item.dto.response.ItemListResponse;
import com.ghm.giftcardfleamarket.item.dto.response.ItemResponse;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemMapper itemMapper;
	private final BrandMapper brandMapper;

	public ItemListResponse getItemsByBrand(Long brandId, int page) {
		List<ItemResponse> itemResponseList = new ArrayList<>();

		Map<String, Object> brandIdAndPageInfo = putIdAndPageInfoToMap(brandId, page, ITEM_PAGE_SIZE.getPageSize());
		List<Item> itemList = itemMapper.selectItemsByBrand(brandIdAndPageInfo);
		itemList.forEach(item -> itemResponseList.add(ItemResponse.of(item)));

		return new ItemListResponse(itemMapper.selectItemTotalCountByBrand(brandId), itemResponseList);
	}

	public ItemDetailResponse getItemDetails(Long itemId) {
		Item item = itemMapper.selectItemDetails(itemId);
		String brandName = brandMapper.selectBrandNameByBrandId(item.getBrandId());

		return ItemDetailResponse.of(item, brandName);
	}
}
