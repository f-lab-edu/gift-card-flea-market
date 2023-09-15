package com.ghm.giftcardfleamarket.domain.user.exception;

public class UnauthorizedUserException extends RuntimeException {

	public UnauthorizedUserException(String message) {
		super(message);
	}
}
