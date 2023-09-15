package com.ghm.giftcardfleamarket.domain.sale.dto.response;

import java.time.LocalDate;

import com.ghm.giftcardfleamarket.domain.sale.model.Inventory;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class InventoryResponse {
	private String brandName;
	private String itemName;
	private LocalDate expirationDate;
	private int count;
	private int salePrice;

	public static InventoryResponse of(Inventory inventory, String brandName, String itemName, int salePrice) {
		return InventoryResponse.builder()
			.brandName(brandName)
			.itemName(itemName)
			.expirationDate(inventory.getExpirationDate())
			.count(inventory.getCount())
			.salePrice(salePrice)
			.build();
	}
}