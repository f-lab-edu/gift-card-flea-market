package com.ghm.giftcardfleamarket.brand.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

	@Mock
	private BrandMapper brandMapper;

	@InjectMocks
	private BrandService brandService;

	private List<String> brandNames;

	@BeforeEach
	void setUp() {
		brandNames = Arrays.asList("스타벅스", "커피빈", "할리스", "공차", "이디야");
	}

	@Test
	@DisplayName("카페 카테고리에 해당하는 브랜드 목록을 조회하는데 성공한다.")
	void getBrandNamesByCategorySuccess() {
		given(brandMapper.selectBrandNamesByCategory(1L)).willReturn(brandNames);

		List<String> result = brandService.getBrandNamesByCategory(1L);

		then(brandMapper).should().selectBrandNamesByCategory(1L);
		assertEquals(result, brandNames);
	}
}