package com.ghm.giftcardfleamarket.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ghm.giftcardfleamarket.sale.exception.DuplicatedBarcodeException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedEmailException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedPhoneException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedUserIdException;
import com.ghm.giftcardfleamarket.user.exception.PasswordMisMatchException;
import com.ghm.giftcardfleamarket.user.exception.UnauthorizedUserException;
import com.ghm.giftcardfleamarket.user.exception.UserIdNotFoundException;
import com.ghm.giftcardfleamarket.user.exception.verification.SmsSendFailedException;
import com.ghm.giftcardfleamarket.user.exception.verification.VerificationCodeMisMatchException;
import com.ghm.giftcardfleamarket.user.exception.verification.VerificationCodeTimeOutException;

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
		int statusCode = Integer.parseInt(e.getErrorCode());
		return new ResponseEntity<>(HttpStatus.valueOf(statusCode));
	}

	@ExceptionHandler(VerificationCodeMisMatchException.class)
	public ResponseEntity<String> handleVerificationCodeMisMatchException(VerificationCodeMisMatchException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(VerificationCodeTimeOutException.class)
	public ResponseEntity<String> handleVerificationCodeTimeOutException(VerificationCodeTimeOutException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserIdNotFoundException.class)
	public ResponseEntity<String> handleUserIdNotFoundException(UserIdNotFoundException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PasswordMisMatchException.class)
	public ResponseEntity<String> handlePasswordMisMatchException(PasswordMisMatchException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UnauthorizedUserException.class)
	public ResponseEntity<String> handleUnauthorizedUserException(UnauthorizedUserException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(DuplicatedBarcodeException.class)
	public ResponseEntity<String> handleDuplicatedBarcodeException(DuplicatedBarcodeException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}
}
