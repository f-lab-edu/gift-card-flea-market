package com.ghm.giftcardfleamarket.domain.user.exception;

public class DuplicatedEmailException extends RuntimeException {

	public DuplicatedEmailException(String message) {
		super(message);
	}
}
