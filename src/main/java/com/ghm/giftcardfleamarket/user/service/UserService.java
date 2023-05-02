package com.ghm.giftcardfleamarket.user.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.user.domain.User;
import com.ghm.giftcardfleamarket.user.dto.request.SignUpRequest;
import com.ghm.giftcardfleamarket.user.encrypt.PasswordEncryptor;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedEmailException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedPhoneException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedUserIdException;
import com.ghm.giftcardfleamarket.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final PasswordEncryptor passwordEncryptor;

	public void singUp(SignUpRequest signUpRequest) {
		String digest = passwordEncryptor.encrypt(signUpRequest.getPassword());
		User newUser = signUpRequest.toEntity(digest);

		try {
			userMapper.saveUser(newUser);
		} catch (DuplicateKeyException e) {
			if (e.getMessage().contains(newUser.getUserId())) {
				throw new DuplicatedUserIdException("중복된 아이디입니다.");
			}
			if (e.getMessage().contains(newUser.getEmail())) {
				throw new DuplicatedEmailException("중복된 이메일입니다.");
			}
			if (e.getMessage().contains(newUser.getPhone())) {
				throw new DuplicatedPhoneException("중복된 휴대폰 번호입니다.");
			}
		}
	}

	public void checkUserIdDuplication(String userId) {
		if (userMapper.hasUserId(userId)) {
			throw new DuplicatedUserIdException("중복된 아이디입니다.");
		}
	}
}
