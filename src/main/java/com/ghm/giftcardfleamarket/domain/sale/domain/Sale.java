package com.ghm.giftcardfleamarket.domain.sale.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sale {
	private Long id;
	private Long itemId;
	private String sellerId;
	private String barcode;
	private LocalDate expirationDate;
	private boolean purchaseStatus;
	private boolean expirationStatus;
	private LocalDateTime registeredAt;
	private LocalDateTime updatedAt;
}