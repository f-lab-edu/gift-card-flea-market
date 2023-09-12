package com.ghm.giftcardfleamarket.purchase.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ghm.giftcardfleamarket.purchase.dto.request.PurchaseRequest;
import com.ghm.giftcardfleamarket.purchase.dto.response.AvailablePurchaseDetailResponse;
import com.ghm.giftcardfleamarket.purchase.dto.response.AvailablePurchaseListResponse;
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

	@GetMapping
	public ResponseEntity<AvailablePurchaseListResponse> getMyAvailableGiftCards(
		@RequestParam(defaultValue = "0") int page) {
		return new ResponseEntity<>(purchaseService.getMyAvailableGiftCards(page), HttpStatus.OK);
	}

	@GetMapping("/{purchaseId}")
	public ResponseEntity<AvailablePurchaseDetailResponse> getMyAvailableGiftCardDetails(
		@PathVariable Long purchaseId) {
		return new ResponseEntity<>(purchaseService.getMyAvailableGiftCardDetails(purchaseId), HttpStatus.OK);
	}

	@PatchMapping("/{purchaseId}")
	public ResponseEntity<Void> confirmGiftCardUsage(@PathVariable Long purchaseId) {
		purchaseService.confirmGiftCardUsage(purchaseId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}