package com.ghm.giftcardfleamarket.user.service;

import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.user.domain.User;
import com.ghm.giftcardfleamarket.user.dto.request.SignUpRequest;
import com.ghm.giftcardfleamarket.user.encrypt.BcryptPasswordEncryptor;
import com.ghm.giftcardfleamarket.user.exception.NotUniqueUserIdException;
import com.ghm.giftcardfleamarket.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final BcryptPasswordEncryptor passwordEncryptor;

	public void singUp(SignUpRequest signUpRequest) {
		checkUserIdDuplicate(signUpRequest.getUserId());    // 아이디 중복체크

		String digest = passwordEncryptor.encrypt(signUpRequest.getPassword());    // 비밀번호 암호화
		User newUser = signUpRequest.toEntity(digest);    // UserDto to Entity
		userMapper.saveUser(newUser);
	}

	public void checkUserIdDuplicate(String userId) {
		if (userMapper.hasUserId(userId)) {
			throw new NotUniqueUserIdException("아이디 중복! 다시 입력해 주세요.");
		}
	}
}
