package com.ghm.giftcardfleamarket.item.dto.response;

import static com.ghm.giftcardfleamarket.common.utils.constants.PriceRate.*;

import com.ghm.giftcardfleamarket.item.domain.Item;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemResponse {
	private String name;
	private int price;
	private int discountPrice;

	public static ItemResponse of(Item item) {
		return ItemResponse.builder()
			.name(item.getName())
			.price(item.getPrice())
			.discountPrice(calculatePrice(item.getPrice(), DISCOUNT_RATE.getRate()))
			.build();
	}
}
