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

	@PostMapping
	public ResponseEntity<String> signUp(@RequestBody @Validated SignUpRequest signUpRequest) {
		userService.singUp(signUpRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/check-duplication/{userId}")
	public ResponseEntity<Boolean> checkUserIdDuplication(@PathVariable String userId) {
		userService.checkUserIdDuplicate(userId);
		return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
	}
}
