package com.ghm.giftcardfleamarket.brand.dto;

import com.ghm.giftcardfleamarket.brand.domain.Brand;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BrandResponse {
	private String name;

	public static BrandResponse of(Brand brand) {
		return new BrandResponse(brand.getName());
	}
}
