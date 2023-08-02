package com.ghm.giftcardfleamarket.common.utils.constants;

public enum PriceRate {
	DISCOUNT_RATE(10),
	HIGH_DISCOUNT_RATE(15),
	PROPOSAL_RATE(20);

	private final double rate;

	PriceRate(double rate) {
		this.rate = rate;
	}

	public double getRate() {
		return rate;
	}
}
