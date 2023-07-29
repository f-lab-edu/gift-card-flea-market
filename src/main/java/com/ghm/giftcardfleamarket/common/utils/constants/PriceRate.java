package com.ghm.giftcardfleamarket.common.utils.constants;

public enum PriceRate {
	DISCOUNT_RATE(10),
	HIGH_DISCOUNT_RATE(15),
	PROPOSAL_RATE(20);

	private final double rate;

	PriceRate(double rate) {
		this.rate = rate;
	}

	public static int calculatePrice(int price, double rate) {
		int calculatedPrice = price - (int)(price * rate / 100);
		return removeLastDigit(calculatedPrice);
	}

	public double getRate() {
		return rate;
	}

	private static int removeLastDigit(int price) {
		return price / 10 * 10;
	}
}
