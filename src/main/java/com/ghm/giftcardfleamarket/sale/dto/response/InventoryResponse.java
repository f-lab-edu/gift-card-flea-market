package com.ghm.giftcardfleamarket.sale.dto.response;

import java.time.LocalDate;

import com.ghm.giftcardfleamarket.sale.domain.Inventory;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryResponse {
	private LocalDate expirationDate;
	private int count;
	private int salePrice;

	public static InventoryResponse of(Inventory inventory, int salePrice) {
		return InventoryResponse.builder()
			.expirationDate(inventory.getExpirationDate())
			.count(inventory.getCount())
			.salePrice(salePrice)
			.build();
	}
}