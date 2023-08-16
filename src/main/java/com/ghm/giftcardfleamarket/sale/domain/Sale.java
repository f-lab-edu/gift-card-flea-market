package com.ghm.giftcardfleamarket.sale.domain;

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
	private String sellerId;
	private Long itemId;
	private String barcode;
	private LocalDate expirationDate;
	private LocalDateTime registeredAt;
}