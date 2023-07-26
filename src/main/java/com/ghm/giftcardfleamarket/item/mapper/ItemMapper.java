package com.ghm.giftcardfleamarket.item.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.item.domain.Item;

@Mapper
public interface ItemMapper {

	List<Item> selectItemsByBrand(Long brandId);
}
