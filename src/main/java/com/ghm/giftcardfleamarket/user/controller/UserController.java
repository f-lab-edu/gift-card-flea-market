package com.ghm.giftcardfleamarket.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ghm.giftcardfleamarket.user.domain.User;
import com.ghm.giftcardfleamarket.user.dto.request.LoginRequest;
import com.ghm.giftcardfleamarket.user.dto.request.SignUpRequest;
import com.ghm.giftcardfleamarket.user.dto.request.SmsVerificationRequest;
import com.ghm.giftcardfleamarket.user.service.LoginService;
import com.ghm.giftcardfleamarket.user.service.UserService;
import com.ghm.giftcardfleamarket.user.service.verification.SmsVerificationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final SmsVerificationService smsVerificationService;
	private final LoginService loginService;

	@PostMapping
	public ResponseEntity<String> signUp(@RequestBody @Validated SignUpRequest signUpRequest) {
		userService.signUp(signUpRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/check-duplication")
	public ResponseEntity<Void> checkUserIdDuplication(@RequestParam String userId) {
		userService.checkUserIdDuplication(userId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/sms-verification")
	public ResponseEntity<String> sendSms(@RequestBody SmsVerificationRequest smsRequest) throws
		JsonProcessingException {

		smsVerificationService.sendSms(smsRequest.getPhone());
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@PostMapping("/sms-verification/verify")
	public ResponseEntity<String> verify(@RequestBody @Validated SmsVerificationRequest smsRequest) {
		smsVerificationService.verifyVerificationCode(smsRequest);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody @Validated LoginRequest loginRequest) {
		User user = userService.findUser(loginRequest);
		loginService.login(user.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/logout")
	public ResponseEntity<String> logout() {
		loginService.logout();
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
