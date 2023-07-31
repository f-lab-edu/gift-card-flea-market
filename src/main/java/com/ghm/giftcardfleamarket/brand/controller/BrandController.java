package com.ghm.giftcardfleamarket.brand.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ghm.giftcardfleamarket.brand.dto.BrandResponse;
import com.ghm.giftcardfleamarket.brand.service.BrandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

	private final BrandService brandService;

	@GetMapping("/{categoryId}")
	public ResponseEntity<List<BrandResponse>> getBrandNames(@PathVariable Long categoryId,
		@RequestParam(defaultValue = "0") int page) {
		List<BrandResponse> brandResponseList = brandService.getBrandsByCategory(categoryId, page);
		return new ResponseEntity<>(brandResponseList, HttpStatus.OK);
	}
}
