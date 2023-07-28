package com.ghm.giftcardfleamarket.item.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.common.utils.constants.Pagination;
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

		List<Item> itemList = itemMapper.selectItemsByBrand(putBrandIdAndPageInfoToMap(brandId, page));
		itemList.forEach(item -> itemResponseList.add(item.toDto()));

		return new ItemListResponse(itemMapper.itemTotalCount(brandId), itemResponseList);
	}

	private Map<String, Object> putBrandIdAndPageInfoToMap(Long brandId, int page) {
		Pageable pageable = PageRequest.of(page, Pagination.PAGE_SIZE);
		return Map.ofEntries(
			Map.entry("brandId", brandId),
			Map.entry("pageSize", Pagination.PAGE_SIZE),
			Map.entry("offset", pageable.getOffset()));
	}
}
