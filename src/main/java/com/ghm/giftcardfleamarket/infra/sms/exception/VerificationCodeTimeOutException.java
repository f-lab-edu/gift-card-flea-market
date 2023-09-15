package com.ghm.giftcardfleamarket.infra.sms.exception;

public class VerificationCodeTimeOutException extends RuntimeException {

	public VerificationCodeTimeOutException(String message) {
		super(message);
	}
}
