package com.ghm.giftcardfleamarket.common.utils;

public class PriceCalculationUtil {

	public static final double DISCOUNT_RATE = 10;

	public static int calculatePrice(int price, double rate) {
		int calculatedPrice = price - (int)(price * rate / 100);
		return removeLastDigit(calculatedPrice);
	}

	private static int removeLastDigit(int price) {
		return price / 10 * 10;
	}
}
