package com.ghm.giftcardfleamarket.common.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ghm.giftcardfleamarket.user.exception.DuplicatedEmailException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedPhoneException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedUserIdException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicatedUserIdException.class)
	public ResponseEntity<String> handleDuplicatedUserIdException(DuplicatedUserIdException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DuplicatedEmailException.class)
	public ResponseEntity<String> handleDuplicatedEmailException(DuplicatedEmailException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DuplicatedPhoneException.class)
	public ResponseEntity<String> handleDuplicatedPhoneException(DuplicatedPhoneException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}
}
