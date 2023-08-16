package com.ghm.giftcardfleamarket.brand.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.Page.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.brand.domain.Brand;
import com.ghm.giftcardfleamarket.brand.dto.BrandResponse;
import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.sale.dto.response.SaleOptionResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService {

	private final BrandMapper brandMapper;

	public List<BrandResponse> getBrandsByCategory(Long categoryId, int page) {
		ArrayList<BrandResponse> brandResponseList = new ArrayList<>();

		Map<String, Object> categoryIdAndPageInfo = putIdAndPageInfoToMap(categoryId, page,
			BRAND_PAGE_SIZE.getPageSize());
		List<Brand> brandList = brandMapper.selectBrandsByCategory(categoryIdAndPageInfo);
		brandList.forEach(brand -> brandResponseList.add(BrandResponse.of(brand)));

		return brandResponseList;
	}

	public SaleOptionResponse getBrandNamesByCategory(Long categoryId) {
		Map<String, Object> categoryIdMap = Map.of("id", categoryId);
		List<Brand> brandList = brandMapper.selectBrandsByCategory(categoryIdMap);

		return SaleOptionResponse.ofBrandList(brandList);
	}
}
