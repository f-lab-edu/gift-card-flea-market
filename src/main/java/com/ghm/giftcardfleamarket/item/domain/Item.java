package com.ghm.giftcardfleamarket.item.domain;

import static com.ghm.giftcardfleamarket.common.utils.PriceCalculationUtil.*;

import java.time.LocalDateTime;

import com.ghm.giftcardfleamarket.item.dto.response.ItemResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Item {
	private Long id;
	private String name;
	private int price;
	private Long brandId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Item(String name, int price) {
		this.name = name;
		this.price = price;
	}

	public ItemResponse toDto() {
		return ItemResponse.builder()
			.name(name)
			.price(price)
			.discountPrice(calculatePrice(price, DISCOUNT_RATE))
			.build();
	}
}
