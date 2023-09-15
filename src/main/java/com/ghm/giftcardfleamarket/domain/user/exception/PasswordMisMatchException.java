package com.ghm.giftcardfleamarket.domain.user.exception;

public class PasswordMisMatchException extends RuntimeException {

	public PasswordMisMatchException(String message) {
		super(message);
	}
}
