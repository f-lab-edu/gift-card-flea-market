package com.ghm.giftcardfleamarket.domain.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ghm.giftcardfleamarket.domain.category.mapper.CategoryMapper;
import com.ghm.giftcardfleamarket.domain.sale.exception.SaleOptionListNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryMapper categoryMapper;

	public List<String> getAllCategoryName() {
		List<String> categoryList = categoryMapper.selectAllCategoryName();

		if (CollectionUtils.isEmpty(categoryList)) {
			throw new SaleOptionListNotFoundException("카테고리 목록을 찾을 수 없습니다.");
		}

		return categoryList;
	}
}
