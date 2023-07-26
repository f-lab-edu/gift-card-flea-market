package com.ghm.giftcardfleamarket.category.service;

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

import com.ghm.giftcardfleamarket.category.mapper.CategoryMapper;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	private CategoryMapper categoryMapper;

	@InjectMocks
	private CategoryService categoryService;

	private List<String> categoryNames;

	@BeforeEach
	void setUp() {
		categoryNames = Arrays.asList("카페", "치킨", "피자", "편의점", "숙박", "영화");
	}

	@Test
	@DisplayName("카테고리 목록을 조회하는데 성공한다.")
	void getCategoryNamesSuccess() {
		given(categoryMapper.selectCategoryNames()).willReturn(categoryNames);

		List<String> result = categoryService.getCategoryNames();

		then(categoryMapper).should().selectCategoryNames();
		assertEquals(result, categoryNames);
	}
}