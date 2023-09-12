package com.ghm.giftcardfleamarket.purchase.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsedOrExpiredPurchaseListResponse {
	private List<UsedOrExpiredPurchaseResponse> list;

	public static UsedOrExpiredPurchaseListResponse empty() {
		return null;
	}
}