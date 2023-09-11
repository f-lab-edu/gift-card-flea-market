package com.ghm.giftcardfleamarket.purchase.exception;

public class PurchaseGiftCardNotFoundException extends RuntimeException {

	public PurchaseGiftCardNotFoundException(Long purchaseId) {
		super(purchaseId + "에 해당하는 구매 기프티콘이 없습니다.");
	}
}