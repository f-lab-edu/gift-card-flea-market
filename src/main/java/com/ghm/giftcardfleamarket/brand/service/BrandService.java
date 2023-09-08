package com.ghm.giftcardfleamarket.brand.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PageSize.*;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ghm.giftcardfleamarket.brand.domain.Brand;
import com.ghm.giftcardfleamarket.brand.dto.response.BrandListResponse;
import com.ghm.giftcardfleamarket.brand.dto.response.BrandResponse;
import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.sale.dto.response.SaleOptionResponse;
import com.ghm.giftcardfleamarket.sale.exception.SaleOptionListNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService {

	private final BrandMapper brandMapper;

	public BrandListResponse getBrandsByCategory(Long categoryId, int page) {
		Map<String, Object> categoryIdAndPageInfo = makePagingQueryParamsWithMap(categoryId, page, BRAND.getValue());
		List<Brand> brandList = brandMapper.selectBrandsByCategory(categoryIdAndPageInfo);

		List<BrandResponse> brandResponseList =
			brandList.stream()
				.map(BrandResponse::of)
				.toList();

		return new BrandListResponse(brandResponseList);
	}

	public SaleOptionResponse getBrandNamesByCategory(Long categoryId) {
		Map<String, Object> idToCategoryId = Map.of("id", categoryId);
		List<Brand> brandList = brandMapper.selectBrandsByCategory(idToCategoryId);

		if (CollectionUtils.isEmpty(brandList)) {
			throw new SaleOptionListNotFoundException("브랜드 목록을 찾을 수 없습니다.");
		}

		return SaleOptionResponse.ofBrandList(brandList);
	}
}
