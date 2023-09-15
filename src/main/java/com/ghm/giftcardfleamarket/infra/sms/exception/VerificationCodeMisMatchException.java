package com.ghm.giftcardfleamarket.infra.sms.exception;

public class VerificationCodeMisMatchException extends RuntimeException {

	public VerificationCodeMisMatchException(String message) {
		super(message);
	}
}
