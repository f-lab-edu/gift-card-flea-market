package com.ghm.giftcardfleamarket.brand.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService {

	private final BrandMapper brandMapper;

	public List<String> getBrandNames(Long categoryId) {
		return brandMapper.selectBrandNames(categoryId);
	}
}
