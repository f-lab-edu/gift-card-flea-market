package com.ghm.giftcardfleamarket.sale.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.sale.domain.Sale;

@Mapper
public interface SaleMapper {

	void insertGiftCard(Sale sale);

	boolean hasBarcode(String barcode);

	List<Sale> selectMySoldGiftCards(Map<String, Object> userIdAndPageInfo);
}