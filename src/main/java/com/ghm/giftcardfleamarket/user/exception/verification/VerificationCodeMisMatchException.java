package com.ghm.giftcardfleamarket.user.exception.verification;

public class VerificationCodeMisMatchException extends RuntimeException {
	
	public VerificationCodeMisMatchException(String message) {
		super(message);
	}
}
