package com.ghm.giftcardfleamarket.domain.sale.mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.domain.sale.domain.Sale;
import com.ghm.giftcardfleamarket.domain.sale.model.Inventory;

@Mapper
public interface SaleMapper {

	void insertSaleGiftCard(Sale sale);

	boolean hasBarcode(String barcode);

	List<Sale> selectMySoldGiftCards(Map<String, Object> userIdAndPageInfo);

	List<Inventory> selectGiftCardInventoriesByExpirationDate(Long itemId);

	Optional<Sale> selectSaleGiftCardDetails(Long saleId);

	void updatePurchaseStatus(Map<String, Object> saleIdAndPurchaseStatus);
}