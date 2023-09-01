package com.ghm.giftcardfleamarket.sale.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.sale.domain.Sale;
import com.ghm.giftcardfleamarket.sale.dto.Inventory;

@Mapper
public interface SaleMapper {

	void insertSaleGiftCard(Sale sale);

	boolean hasBarcode(String barcode);

	List<Sale> selectMySoldGiftCards(Map<String, Object> userIdAndPageInfo);

	List<Inventory> selectGiftCardInventoriesByExpirationDate(Long itemId);

	void updatePurchaseStatus(Long saleId);
}