package com.ghm.giftcardfleamarket.item.exception;

public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException(Long itemId) {
		super(itemId + "에 해당하는 상품이 없습니다.");
	}
}