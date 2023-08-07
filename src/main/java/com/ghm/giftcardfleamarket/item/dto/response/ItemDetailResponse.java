package com.ghm.giftcardfleamarket.item.dto.response;

import static com.ghm.giftcardfleamarket.common.utils.PriceCalculationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PriceRate.*;

import com.ghm.giftcardfleamarket.item.domain.Item;

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
			.discountPrice(calculatePrice(item.getPrice(), DISCOUNT_RATE.getRate()))
			.brandName(brandName)
			.build();
	}
}

