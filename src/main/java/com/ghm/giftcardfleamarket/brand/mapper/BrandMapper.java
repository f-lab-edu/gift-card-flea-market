package com.ghm.giftcardfleamarket.brand.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.brand.domain.Brand;

@Mapper
public interface BrandMapper {

	List<Brand> selectBrandsByCategory(Map<String, Object> map);
}
