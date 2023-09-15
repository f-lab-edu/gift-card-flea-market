package com.ghm.giftcardfleamarket.global.util.constants;

public enum PageSize {
	BRAND(9),
	ITEM(4),
	SALE(3),
	PURCHASE(4);

	private final int value;

	PageSize(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
