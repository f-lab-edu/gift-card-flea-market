package com.ghm.giftcardfleamarket.purchase.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ghm.giftcardfleamarket.purchase.domain.Purchase;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AvailablePurchaseDetailResponse {
	private String brandName;
	private String itemName;
	private String barcode;
	private LocalDate expirationDate;
	private int purchasePrice;
	private LocalDateTime boughtAt;

	public static AvailablePurchaseDetailResponse of(Purchase purchase, String brandName, String itemName,
		String barcode, LocalDate expirationDate) {
		return AvailablePurchaseDetailResponse.builder()
			.brandName(brandName)
			.itemName(itemName)
			.barcode(barcode)
			.expirationDate(expirationDate)
			.purchasePrice(purchase.getPrice())
			.boughtAt(purchase.getBoughtAt())
			.build();
	}
}