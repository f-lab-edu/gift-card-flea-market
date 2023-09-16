package com.ghm.giftcardfleamarket.domain.item.dto.response;

import static com.ghm.giftcardfleamarket.global.util.PriceCalculationUtil.*;
import static com.ghm.giftcardfleamarket.global.util.constants.PriceRate.*;

import com.ghm.giftcardfleamarket.domain.item.domain.Item;

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
			.discountPrice(calculatePrice(item.getPrice(), STANDARD_DISCOUNT))
			.build();
	}
}
