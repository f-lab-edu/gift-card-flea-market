package com.ghm.giftcardfleamarket.domain.purchase.dto.response;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.ghm.giftcardfleamarket.domain.purchase.domain.Purchase;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AvailablePurchaseResponse {
	private String brandName;
	private String itemName;
	private int price;
	private int purchasePrice;
	private LocalDate expirationDate;
	private long daysUntilExpirationDate;

	public static AvailablePurchaseResponse of(Purchase purchase, String brandName, String itemName, int price,
		LocalDate expirationDate) {
		return AvailablePurchaseResponse.builder()
			.brandName(brandName)
			.itemName(itemName)
			.price(price)
			.purchasePrice(purchase.getPrice())
			.expirationDate(expirationDate)
			.daysUntilExpirationDate(LocalDate.now().until(expirationDate, ChronoUnit.DAYS))
			.build();
	}
}