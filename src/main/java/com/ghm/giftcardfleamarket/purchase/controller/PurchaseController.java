package com.ghm.giftcardfleamarket.purchase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ghm.giftcardfleamarket.purchase.dto.request.PurchaseRequest;
import com.ghm.giftcardfleamarket.purchase.service.PurchaseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

	private final PurchaseService purchaseService;

	@PostMapping
	public ResponseEntity<Void> buyGiftCard(@RequestBody PurchaseRequest purchaseRequest) {
		purchaseService.buyGiftCard(purchaseRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}