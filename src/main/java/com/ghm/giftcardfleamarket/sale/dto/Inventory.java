package com.ghm.giftcardfleamarket.sale.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class Inventory {
	private LocalDate expirationDate;
	private int count;
}