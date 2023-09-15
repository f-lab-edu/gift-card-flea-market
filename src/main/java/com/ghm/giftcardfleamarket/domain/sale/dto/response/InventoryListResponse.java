package com.ghm.giftcardfleamarket.domain.sale.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InventoryListResponse {
	List<InventoryResponse> inventoryResponseList;
}