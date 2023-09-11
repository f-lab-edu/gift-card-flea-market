package com.ghm.giftcardfleamarket.purchase.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.purchase.domain.Purchase;

@Mapper
public interface PurchaseMapper {

	void insertPurchaseGiftCard(Purchase purchase);

	List<Purchase> selectMyAvailableGiftCards(Map<String, Object> userIdAndPageInfo);

	boolean hasMyAvailablePurchaseInfo(Map<String, Object> userIdAndPurchaseId);

	Purchase selectMyAvailableGiftCardDetails(Long purchaseId);

	void updateUseStatus(Long purchaseId);
}