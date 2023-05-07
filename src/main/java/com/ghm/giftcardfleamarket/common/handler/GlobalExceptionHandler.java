package com.ghm.giftcardfleamarket.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ghm.giftcardfleamarket.user.exception.DuplicatedEmailException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedPhoneException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedUserIdException;
import com.ghm.giftcardfleamarket.user.exception.verification.SmsSendFailedException;
import com.ghm.giftcardfleamarket.user.exception.verification.VerificationCodeMisMatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({
		DuplicatedUserIdException.class,
		DuplicatedEmailException.class,
		DuplicatedPhoneException.class
	})
	public ResponseEntity<String> handleDuplicatedExceptions(RuntimeException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(SmsSendFailedException.class)
	public ResponseEntity<String> handleSmsSendFailedException(SmsSendFailedException e) {
		HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

		switch (e.getErrorCode()) {
			case "400":
				httpStatus = HttpStatus.BAD_REQUEST;
				break;
			case "401":
				httpStatus = HttpStatus.UNAUTHORIZED;
				break;
			case "403":
				httpStatus = HttpStatus.FORBIDDEN;
				break;
			case "404":
				httpStatus = HttpStatus.NOT_FOUND;
				break;
			case "429":
				httpStatus = HttpStatus.TOO_MANY_REQUESTS;
				break;
		}

		return new ResponseEntity<>(httpStatus);
	}

	@ExceptionHandler(VerificationCodeMisMatchException.class)
	public ResponseEntity<String> handleVerificationCodeMisMatchException(VerificationCodeMisMatchException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
