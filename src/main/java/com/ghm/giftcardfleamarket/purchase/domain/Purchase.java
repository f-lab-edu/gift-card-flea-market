package com.ghm.giftcardfleamarket.purchase.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Purchase {
	private Long id;
	private Long saleId;
	private Long itemId;
	private String buyerId;
	private int price;
	private boolean useStatus;
	private LocalDateTime boughtAt;
	private LocalDateTime usedAt;
	private LocalDateTime updatedAt;
}