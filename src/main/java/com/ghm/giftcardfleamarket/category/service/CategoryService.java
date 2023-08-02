package com.ghm.giftcardfleamarket.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.category.mapper.CategoryMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

	private final CategoryMapper categoryMapper;

	public List<String> getAllCategoryName() {
		return categoryMapper.selectAllCategoryName();
	}
}
