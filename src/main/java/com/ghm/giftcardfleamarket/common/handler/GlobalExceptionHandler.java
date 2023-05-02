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

	@ExceptionHandler({
		DuplicatedUserIdException.class,
		DuplicatedEmailException.class,
		DuplicatedPhoneException.class
	})
	public ResponseEntity<String> handleDuplicatedExceptions(RuntimeException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
	}
}
