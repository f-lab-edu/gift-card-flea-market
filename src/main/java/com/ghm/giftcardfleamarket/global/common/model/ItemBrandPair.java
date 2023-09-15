package com.ghm.giftcardfleamarket.global.common.model;

import com.ghm.giftcardfleamarket.domain.item.domain.Item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemBrandPair {
	private Item item;
	private String brandName;
}