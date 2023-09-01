package com.ghm.giftcardfleamarket.purchase.dto.request;

import java.time.LocalDateTime;

import com.ghm.giftcardfleamarket.purchase.domain.Purchase;

import lombok.Getter;

@Getter
public class PurchaseRequest {
	private Long saleId;
	private Long itemId;
	private int purchasePrice;

	public Purchase toEntity(String loginUserId) {
		return Purchase.builder()
			.saleId(saleId)
			.itemId(itemId)
			.buyerId(loginUserId)
			.price(purchasePrice)
			.boughtAt(LocalDateTime.now())
			.build();
	}
}