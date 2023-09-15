package com.ghm.giftcardfleamarket.domain.purchase.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ghm.giftcardfleamarket.domain.purchase.domain.Purchase;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UsedOrExpiredPurchaseResponse {
	private String brandName;
	private String itemName;
	private int price;
	private int purchasePrice;
	private LocalDate expirationDate;
	private boolean expirationStatus;
	private boolean useStatus;
	private LocalDateTime usedAt;

	public static UsedOrExpiredPurchaseResponse of(Purchase purchase, String brandName, String itemName, int price,
		LocalDate expirationDate, boolean expirationStatus) {
		return UsedOrExpiredPurchaseResponse.builder()
			.brandName(brandName)
			.itemName(itemName)
			.price(price)
			.purchasePrice(purchase.getPrice())
			.expirationDate(expirationDate)
			.expirationStatus(expirationStatus)
			.useStatus(purchase.isUseStatus())
			.usedAt(purchase.getUsedAt())
			.build();
	}
}