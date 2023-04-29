package com.ghm.giftcardfleamarket.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ghm.giftcardfleamarket.user.dto.request.SignUpRequest;
import com.ghm.giftcardfleamarket.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	/**
	 * 회원가입 절차 중 총 2번의 아이디 중복체크를 실행합니다.
	 * 	1. 아이디 입력 직후
	 * 	2. 최종 회원가입 요청
	 *
	 * 아이디 입력 직후에 중복된 아이디가 없을지라도,
	 * 최종 회원가입 전에 다른 유저가 동일한 아이디로 가입했을 경우를 대비하기 위함입니다.
	 */
	@PostMapping
	ResponseEntity<String> signUp(@RequestBody @Validated SignUpRequest signUpRequest) {
		userService.singUp(signUpRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/check-duplicate/{userId}")
	public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
		userService.checkUserIdDuplicate(userId);
		return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
	}
}
