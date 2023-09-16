package com.ghm.giftcardfleamarket.domain.category.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

	List<String> selectAllCategoryName();
}
