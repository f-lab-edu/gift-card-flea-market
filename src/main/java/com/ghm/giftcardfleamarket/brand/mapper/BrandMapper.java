package com.ghm.giftcardfleamarket.brand.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BrandMapper {

	List<String> selectBrandNames(Long categoryId);
}
