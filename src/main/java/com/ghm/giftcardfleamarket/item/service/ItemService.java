package com.ghm.giftcardfleamarket.item.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.common.utils.PagingUtil;
import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.dto.response.ItemListResponse;
import com.ghm.giftcardfleamarket.item.dto.response.ItemResponse;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemMapper itemMapper;

	public ItemListResponse getItemsByBrand(Long brandId, int pageNum) {
		List<ItemResponse> itemResponseList = new ArrayList<>();

		Map<String, Object> brandIdAndPageInfo = new HashMap<>();
		brandIdAndPageInfo.put("brandId", brandId);
		brandIdAndPageInfo.put("dataCountPerPage", PagingUtil.dataCountPerPage);
		brandIdAndPageInfo.put("offset", PagingUtil.getOffset(pageNum));

		List<Item> itemList = itemMapper.selectItemsByBrand(brandIdAndPageInfo);
		itemList.forEach(item -> itemResponseList.add(item.toDto()));

		return new ItemListResponse(itemMapper.itemTotalCount(brandId), itemResponseList);
	}
}
