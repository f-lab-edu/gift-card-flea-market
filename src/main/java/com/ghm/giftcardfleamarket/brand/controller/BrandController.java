package com.ghm.giftcardfleamarket.brand.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ghm.giftcardfleamarket.brand.service.BrandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

	private final BrandService brandService;

	@GetMapping("/{categoryId}")
	public ResponseEntity<List<String>> getBrandNames(@PathVariable Long categoryId) {
		List<String> brandList = brandService.getBrandNamesByCategory(categoryId);
		return new ResponseEntity<>(brandList, HttpStatus.OK);
	}
}
