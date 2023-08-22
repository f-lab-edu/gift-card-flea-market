package com.ghm.giftcardfleamarket.sale.dto.response;

import java.util.List;

import com.ghm.giftcardfleamarket.brand.domain.Brand;
import com.ghm.giftcardfleamarket.item.domain.Item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleOptionResponse {

	private List<String> categoryNames;
	private List<String> brandNames;
	private List<String> itemNames;
	private Integer proposalPrice;

	public SaleOptionResponse(Integer proposalPrice) {
		this.proposalPrice = proposalPrice;
	}

	public static SaleOptionResponse ofCategoryList(List<String> list) {
		return SaleOptionResponse.builder()
			.categoryNames(list)
			.build();
	}

	public static SaleOptionResponse ofBrandList(List<Brand> list) {
		List<String> brandNames = list.stream()
			.map(Brand::getName)
			.toList();

		return SaleOptionResponse.builder()
			.brandNames(brandNames)
			.build();
	}

	public static SaleOptionResponse ofItemList(List<Item> list) {
		List<String> itemNames = list.stream()
			.map(Item::getName)
			.toList();

		return SaleOptionResponse.builder()
			.itemNames(itemNames)
			.build();
	}
}