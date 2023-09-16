package com.ghm.giftcardfleamarket.domain.sale.exception;

public class DuplicatedBarcodeException extends RuntimeException {

	public DuplicatedBarcodeException(String message) {
		super(message);
	}
}