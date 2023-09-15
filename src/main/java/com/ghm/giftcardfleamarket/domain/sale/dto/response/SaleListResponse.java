package com.ghm.giftcardfleamarket.domain.sale.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaleListResponse {
	private List<SaleResponse> saleResponseList;

	public static SaleListResponse empty() {
		return null;
	}
}