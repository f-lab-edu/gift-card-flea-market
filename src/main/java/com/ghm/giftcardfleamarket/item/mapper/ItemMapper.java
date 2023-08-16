package com.ghm.giftcardfleamarket.item.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.item.domain.Item;

@Mapper
public interface ItemMapper {

	List<Item> selectItemsByBrand(Map<String, Object> brandIdAndPageInfo);

	int selectItemTotalCountByBrand(Long brandId);

	Item selectItemDetails(Long itemId);

	int selectItemPrice(Long itemId);
}
