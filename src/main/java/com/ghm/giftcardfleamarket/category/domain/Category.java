package com.ghm.giftcardfleamarket.category.domain;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class Category {
	private Long id;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
