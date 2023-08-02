package com.ghm.giftcardfleamarket.item.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ghm.giftcardfleamarket.item.dto.response.ItemDetailResponse;
import com.ghm.giftcardfleamarket.item.dto.response.ItemListResponse;
import com.ghm.giftcardfleamarket.item.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/{brandId}")
	public ResponseEntity<ItemListResponse> getItems(@PathVariable Long brandId,
		@RequestParam(defaultValue = "0") int page) {
		ItemListResponse itemListResponse = itemService.getItemsByBrand(brandId, page);
		return new ResponseEntity<>(itemListResponse, HttpStatus.OK);
	}

	@GetMapping("/details/{itemId}")
	public ResponseEntity<ItemDetailResponse> getItemDetails(@PathVariable Long itemId) {
		return new ResponseEntity<>(itemService.getItemDetails(itemId), HttpStatus.OK);
	}
}
