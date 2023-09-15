package com.ghm.giftcardfleamarket.domain.item.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ghm.giftcardfleamarket.domain.item.dto.response.ItemDetailResponse;
import com.ghm.giftcardfleamarket.domain.item.dto.response.ItemListResponse;
import com.ghm.giftcardfleamarket.domain.item.service.ItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/{brandId}")
	public ResponseEntity<ItemListResponse> getItems(@PathVariable Long brandId,
		@RequestParam(defaultValue = "0") int page) {
		return new ResponseEntity<>(itemService.getItemsByBrand(brandId, page), HttpStatus.OK);
	}

	@GetMapping("/details/{itemId}")
	public ResponseEntity<ItemDetailResponse> getItemDetails(@PathVariable Long itemId) {
		return new ResponseEntity<>(itemService.getItemDetails(itemId), HttpStatus.OK);
	}
}
