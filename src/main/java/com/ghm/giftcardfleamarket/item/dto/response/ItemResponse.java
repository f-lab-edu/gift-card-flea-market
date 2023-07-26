package com.ghm.giftcardfleamarket.item.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponse {
	private String name;
	private int price;
	private int discountPrice;
}
