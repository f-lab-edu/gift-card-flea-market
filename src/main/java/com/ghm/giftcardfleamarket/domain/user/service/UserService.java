package com.ghm.giftcardfleamarket.domain.user.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ghm.giftcardfleamarket.domain.user.domain.User;
import com.ghm.giftcardfleamarket.domain.user.dto.request.LoginRequest;
import com.ghm.giftcardfleamarket.domain.user.dto.request.SignUpRequest;
import com.ghm.giftcardfleamarket.domain.user.exception.DuplicatedEmailException;
import com.ghm.giftcardfleamarket.domain.user.exception.DuplicatedPhoneException;
import com.ghm.giftcardfleamarket.domain.user.exception.DuplicatedUserIdException;
import com.ghm.giftcardfleamarket.domain.user.exception.PasswordMisMatchException;
import com.ghm.giftcardfleamarket.domain.user.exception.UserIdNotFoundException;
import com.ghm.giftcardfleamarket.domain.user.mapper.UserMapper;
import com.ghm.giftcardfleamarket.global.util.PasswordEncryptor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final PasswordEncryptor passwordEncryptor;

	@Transactional
	public void signUp(SignUpRequest signUpRequest) {
		String digest = passwordEncryptor.encrypt(signUpRequest.getPassword());
		User newUser = signUpRequest.toEntity(digest);

		try {
			userMapper.insertUser(newUser);
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

	@Transactional(readOnly = true)
	public void checkUserIdDuplication(String userId) {
		if (userMapper.hasUserId(userId)) {
			throw new DuplicatedUserIdException("중복된 아이디입니다.");
		}
	}

	@Transactional(readOnly = true)
	public User findUser(LoginRequest loginRequest) {
		User loginUser = userMapper.selectUserByUserId(loginRequest.getUserId())
			.orElseThrow(() -> new UserIdNotFoundException("등록되지 않은 아이디입니다."));

		boolean isValidPassword = passwordEncryptor.isMatch(loginRequest.getPassword(), loginUser.getPassword());
		if (!isValidPassword) {
			throw new PasswordMisMatchException("비밀번호가 일치하지 않습니다.");
		}

		return loginUser;
	}
}
