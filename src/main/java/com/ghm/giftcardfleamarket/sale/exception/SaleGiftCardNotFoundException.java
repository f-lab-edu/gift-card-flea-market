package com.ghm.giftcardfleamarket.sale.exception;

public class SaleGiftCardNotFoundException extends RuntimeException {

	public SaleGiftCardNotFoundException(Long saleId) {
		super(saleId + "에 해당하는 판매 기프티콘이 없습니다.");
	}
}