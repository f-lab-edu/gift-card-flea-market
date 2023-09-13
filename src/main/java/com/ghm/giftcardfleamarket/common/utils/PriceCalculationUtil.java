package com.ghm.giftcardfleamarket.common.utils;

import com.ghm.giftcardfleamarket.common.utils.constants.PriceRate;

public class PriceCalculationUtil {

	public static int calculatePrice(int price, PriceRate rate) {
		int calculatedPrice = price - (int)(price * rate.getValue() / 100);
		return removeLastDigit(calculatedPrice);
	}

	private static int removeLastDigit(int price) {
		return price / 10 * 10;
	}
}
