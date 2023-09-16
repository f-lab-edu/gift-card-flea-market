package com.ghm.giftcardfleamarket.domain.sale.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ghm.giftcardfleamarket.domain.sale.domain.Sale;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaleResponse {
	private String itemName;
	private int salePrice;
	private String barcodeLast6Digit;
	private LocalDate expirationDate;
	private LocalDateTime registeredAt;

	public static SaleResponse of(Sale sale, String itemName, int salePrice) {
		return SaleResponse.builder()
			.itemName(itemName)
			.salePrice(salePrice)
			.barcodeLast6Digit(sale.getBarcode().substring(6))
			.expirationDate(sale.getExpirationDate())
			.registeredAt(sale.getRegisteredAt())
			.build();
	}
}