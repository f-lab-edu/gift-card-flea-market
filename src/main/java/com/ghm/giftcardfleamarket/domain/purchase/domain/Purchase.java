package com.ghm.giftcardfleamarket.domain.purchase.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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