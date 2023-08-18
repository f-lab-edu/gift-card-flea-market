package com.ghm.giftcardfleamarket.sale.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.sale.domain.Sale;

@Mapper
public interface SaleMapper {

	void insertSaleGiftCard(Sale sale);

	boolean hasBarcode(String barcode);

	List<Sale> selectSaleGiftCard(Map<String, Object> userIdAndPageInfo);
}