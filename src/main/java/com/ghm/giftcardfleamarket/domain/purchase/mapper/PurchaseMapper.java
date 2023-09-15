package com.ghm.giftcardfleamarket.domain.purchase.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.domain.purchase.domain.Purchase;

@Mapper
public interface PurchaseMapper {

	void insertPurchaseGiftCard(Purchase purchase);

	List<Purchase> selectMyAvailableGiftCards(Map<String, Object> userIdAndPageInfo);

	List<Purchase> selectMyUsedOrExpiredGiftCards(Map<String, Object> userIdAndPageInfo);

	boolean hasMyAvailablePurchaseInfo(Map<String, Object> userIdAndPurchaseId);

	Purchase selectMyAvailableGiftCardDetails(Long purchaseId);

	void updateUseStatus(Long purchaseId);
}