package com.ghm.giftcardfleamarket.domain.sale.model;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class Inventory {
	private LocalDate expirationDate;
	private int count;
}