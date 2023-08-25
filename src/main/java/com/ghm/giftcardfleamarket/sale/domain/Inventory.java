package com.ghm.giftcardfleamarket.sale.domain;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class Inventory {
	private LocalDate expirationDate;
	private int count;
}