package com.ghm.giftcardfleamarket.purchase.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AvailablePurchaseListResponse {
	private List<AvailablePurchaseResponse> list;

	public static AvailablePurchaseListResponse empty() {
		return null;
	}
}