package com.ghm.giftcardfleamarket.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ghm.giftcardfleamarket.user.exception.NotUniqueUserIdException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 클라이언트가 POST 요청한 리소스가 서버에 이미 존재해 충돌하는 경우에 409(Conflict) 코드를 반환합니다.
	@ExceptionHandler(NotUniqueUserIdException.class)
	public ResponseEntity<String> handelNotUniqueUserIdException(NotUniqueUserIdException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}
}
