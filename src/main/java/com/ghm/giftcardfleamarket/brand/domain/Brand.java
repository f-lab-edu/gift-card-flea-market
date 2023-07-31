package com.ghm.giftcardfleamarket.brand.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Brand {
	private Long id;
	private String name;
	private Long categoryId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public Brand(String name) {
		this.name = name;
	}
}
