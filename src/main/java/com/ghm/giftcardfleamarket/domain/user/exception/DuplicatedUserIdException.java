package com.ghm.giftcardfleamarket.domain.user.exception;

public class DuplicatedUserIdException extends RuntimeException {

	public DuplicatedUserIdException(String message) {
		super(message);
	}
}
