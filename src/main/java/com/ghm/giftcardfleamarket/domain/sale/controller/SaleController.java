package com.ghm.giftcardfleamarket.domain.sale.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ghm.giftcardfleamarket.domain.brand.service.BrandService;
import com.ghm.giftcardfleamarket.domain.category.service.CategoryService;
import com.ghm.giftcardfleamarket.domain.item.service.ItemService;
import com.ghm.giftcardfleamarket.domain.sale.dto.request.SaleRequest;
import com.ghm.giftcardfleamarket.domain.sale.dto.response.InventoryListResponse;
import com.ghm.giftcardfleamarket.domain.sale.dto.response.SaleListResponse;
import com.ghm.giftcardfleamarket.domain.sale.dto.response.SaleOptionResponse;
import com.ghm.giftcardfleamarket.domain.sale.service.SaleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

	private final SaleService saleService;
	private final CategoryService categoryService;
	private final BrandService brandService;
	private final ItemService itemService;

	@GetMapping("/options/categories")
	public ResponseEntity<SaleOptionResponse> getAllCategoryName() {
		List<String> allCategoryName = categoryService.getAllCategoryName();
		return new ResponseEntity<>(SaleOptionResponse.ofCategoryList(allCategoryName), HttpStatus.OK);
	}

	@GetMapping("/options/brands/{categoryId}")
	public ResponseEntity<SaleOptionResponse> getBrandNames(@PathVariable Long categoryId) {
		return new ResponseEntity<>(brandService.getBrandNamesByCategory(categoryId), HttpStatus.OK);
	}

	@GetMapping("/options/items/{brandId}")
	public ResponseEntity<SaleOptionResponse> getItemNames(@PathVariable Long brandId) {
		return new ResponseEntity<>(itemService.getItemNamesByBrand(brandId), HttpStatus.OK);
	}

	@GetMapping("/options/item-price/{itemId}")
	public ResponseEntity<SaleOptionResponse> getItemProposalPrice(@PathVariable Long itemId) {
		return new ResponseEntity<>(itemService.getItemProposalPrice(itemId), HttpStatus.OK);
	}

	@PostMapping("/{itemId}")
	public ResponseEntity<Void> sellGiftCard(@PathVariable Long itemId,
		@RequestBody @Validated SaleRequest saleRequest) {
		saleRequest.setItemId(itemId);
		saleService.sellGiftCard(saleRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<SaleListResponse> getMySoldGiftCards(@RequestParam(defaultValue = "0") int page) {
		return new ResponseEntity<>(saleService.getMySoldGiftCards(page), HttpStatus.OK);
	}

	@GetMapping("/{itemId}")
	public ResponseEntity<InventoryListResponse> getGiftCardInventories(@PathVariable Long itemId) {
		return new ResponseEntity<>(saleService.getGiftCardInventoriesByExpirationDate(itemId), HttpStatus.OK);
	}
}