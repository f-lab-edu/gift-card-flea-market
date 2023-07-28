package com.ghm.giftcardfleamarket.item.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemListResponse {
	private int totalCount;
	private List<ItemResponse> itemResponseList;
}
