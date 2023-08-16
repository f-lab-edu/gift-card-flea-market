package com.ghm.giftcardfleamarket.sale.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.sale.domain.Sale;

@Mapper
public interface SaleMapper {

	void insertSaleGiftCard(Sale sale);

	boolean hasBarcode(String barcode);
}