package com.ghm.giftcardfleamarket.item.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.Page.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.dto.response.ItemListResponse;
import com.ghm.giftcardfleamarket.item.dto.response.ItemResponse;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemMapper itemMapper;

	public ItemListResponse getItemsByBrand(Long brandId, int page) {
		List<ItemResponse> itemResponseList = new ArrayList<>();

		Map<String, Object> brandIdAndPageInfo = putIdAndPageInfoToMap(brandId, page, ITEM_PAGE_SIZE.getPageSize());
		List<Item> itemList = itemMapper.selectItemsByBrand(brandIdAndPageInfo);
		itemList.forEach(item -> itemResponseList.add(ItemResponse.of(item)));

		return new ItemListResponse(itemMapper.itemTotalCount(brandId), itemResponseList);
	}
}
