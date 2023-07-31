package com.ghm.giftcardfleamarket.brand.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Brand {
	private Long id;
	private String name;
	private Long categoryId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
