package com.ghm.giftcardfleamarket.category.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ghm.giftcardfleamarket.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping
	public ResponseEntity<List<String>> getAllCategoryName() {
		List<String> categoryList = categoryService.getAllCategoryName();
		return new ResponseEntity<>(categoryList, HttpStatus.OK);
	}
}
