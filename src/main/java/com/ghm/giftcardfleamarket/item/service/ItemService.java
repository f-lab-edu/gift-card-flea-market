package com.ghm.giftcardfleamarket.item.service;

import java.util.ArrayList;
import java.util.List;

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

	public ItemListResponse getItemsByBrand(Long brandId) {
		List<ItemResponse> itemResponseList = new ArrayList<>();

		List<Item> itemList = itemMapper.selectItemsByBrand(brandId);
		itemList.forEach(item -> itemResponseList.add(item.toDto()));

		return new ItemListResponse(itemResponseList.size(), itemResponseList);
	}
}
