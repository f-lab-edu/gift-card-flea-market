package com.ghm.giftcardfleamarket.item.domain;

import java.time.LocalDateTime;

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
}
