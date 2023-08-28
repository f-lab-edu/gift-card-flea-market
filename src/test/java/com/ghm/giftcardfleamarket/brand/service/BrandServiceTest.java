package com.ghm.giftcardfleamarket.brand.service;

import static com.ghm.giftcardfleamarket.common.utils.constants.Page.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.ghm.giftcardfleamarket.brand.domain.Brand;
import com.ghm.giftcardfleamarket.brand.dto.response.BrandListResponse;
import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

	@Mock
	private BrandMapper brandMapper;

	@InjectMocks
	private BrandService brandService;

	private Map<String, Object> categoryIdAndPageInfo;

	private List<Brand> brandList;

	@BeforeEach
	void setUp() {

		categoryIdAndPageInfo = Map.ofEntries(
			Map.entry("id", 1L),
			Map.entry("pageSize", BRAND_PAGE_SIZE.getPageSize()),
			Map.entry("offset", PageRequest.of(0, BRAND_PAGE_SIZE.getPageSize()).getOffset()));

		brandList = Arrays.asList(
			new Brand("스타벅스"),
			new Brand("커피빈"),
			new Brand("할리스"),
			new Brand("공차"),
			new Brand("이디야"));
	}

	@Test
	@DisplayName("카페 카테고리에 해당하는 브랜드 목록을 조회하는데 성공한다.")
	void getBrandsByCategorySuccess() {
		given(brandMapper.selectBrandsByCategory(categoryIdAndPageInfo)).willReturn(brandList);

		BrandListResponse result = brandService.getBrandsByCategory(1L, 0);

		then(brandMapper).should().selectBrandsByCategory(categoryIdAndPageInfo);
		assertEquals(result.getBrandResponseList().size(), brandList.size());
	}
}