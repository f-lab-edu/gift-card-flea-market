package com.ghm.giftcardfleamarket.domain.item.dto.response;

import static com.ghm.giftcardfleamarket.global.util.PriceCalculationUtil.*;
import static com.ghm.giftcardfleamarket.global.util.constants.PriceRate.*;

import com.ghm.giftcardfleamarket.domain.item.domain.Item;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ItemDetailResponse {
	private String itemName;
	private int price;
	private int discountPrice;
	private String brandName;

	public static ItemDetailResponse of(Item item, String brandName) {
		return ItemDetailResponse.builder()
			.itemName(item.getName())
			.price(item.getPrice())
			.discountPrice(calculatePrice(item.getPrice(), STANDARD_DISCOUNT))
			.brandName(brandName)
			.build();
	}
}

