package com.ghm.giftcardfleamarket.item.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.item.domain.Item;

@Mapper
public interface ItemMapper {

	List<Item> selectItemsByBrand(Map<String, Object> map);

	int selectItemTotalCountByBrand(Long brandId);

	Optional<Item> selectItemDetails(Long itemId);
}
