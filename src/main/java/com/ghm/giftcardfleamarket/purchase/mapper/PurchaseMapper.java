package com.ghm.giftcardfleamarket.purchase.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.purchase.domain.Purchase;

@Mapper
public interface PurchaseMapper {

	void insertPurchaseGiftCard(Purchase purchase);
}