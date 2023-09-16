package com.ghm.giftcardfleamarket.infra.sms.exception;

public class SmsSendFailedException extends RuntimeException {

	private String errorCode;

	public SmsSendFailedException(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
}
