package com.ghm.giftcardfleamarket.common;

import com.ghm.giftcardfleamarket.item.domain.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemBrandPair {
	private Item item;
	private String brandName;
}