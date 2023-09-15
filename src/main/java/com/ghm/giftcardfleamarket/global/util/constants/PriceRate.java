package com.ghm.giftcardfleamarket.global.util.constants;

public enum PriceRate {
	STANDARD_DISCOUNT(10),
	HIGH_DISCOUNT(15),
	PROPOSAL(20);

	private final double value;

	PriceRate(double value) {
		this.value = value;
	}

	public double getValue() {
		return value;
	}
}
