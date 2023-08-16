package com.ghm.giftcardfleamarket.sale.dto.response;

import java.util.ArrayList;
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

	List<String> categoryName;
	List<String> brandName;
	List<String> itemName;
	int proposalPrice;

	public SaleOptionResponse(int proposalPrice) {
		this.proposalPrice = proposalPrice;
	}

	public static SaleOptionResponse ofCategoryList(List<String> list) {
		SaleOptionResponse saleOptionResponse = SaleOptionResponse.builder()
			.categoryName(new ArrayList<>())
			.build();

		list.forEach(categoryName -> saleOptionResponse.getCategoryName().add(categoryName));
		return saleOptionResponse;
	}

	public static SaleOptionResponse ofBrandList(List<Brand> list) {
		SaleOptionResponse saleOptionResponse = SaleOptionResponse.builder()
			.brandName(new ArrayList<>())
			.build();

		list.forEach(brand -> saleOptionResponse.getBrandName().add(brand.getName()));
		return saleOptionResponse;
	}

	public static SaleOptionResponse ofItemList(List<Item> list) {
		SaleOptionResponse saleOptionResponse = SaleOptionResponse.builder()
			.itemName(new ArrayList<>())
			.build();

		list.forEach(item -> saleOptionResponse.getItemName().add(item.getName()));
		return saleOptionResponse;
	}
}