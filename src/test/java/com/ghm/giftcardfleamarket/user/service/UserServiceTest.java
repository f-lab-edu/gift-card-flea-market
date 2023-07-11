package com.ghm.giftcardfleamarket.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import com.ghm.giftcardfleamarket.common.utils.encrypt.PasswordEncryptor;
import com.ghm.giftcardfleamarket.user.domain.User;
import com.ghm.giftcardfleamarket.user.dto.request.SignUpRequest;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedEmailException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedPhoneException;
import com.ghm.giftcardfleamarket.user.exception.DuplicatedUserIdException;
import com.ghm.giftcardfleamarket.user.mapper.UserMapper;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserMapper userMapper;

	@Mock
	private PasswordEncryptor passwordEncryptor;

	@InjectMocks
	private UserService userService;

	private SignUpRequest signUpRequest;

	@BeforeEach
	void setUp() {
		signUpRequest = SignUpRequest.builder()
			.userId("testUserId")
			.password("testPassword")
			.email("test@example.com")
			.phone("01012345678")
			.build();
	}

	@Test
	@DisplayName("회원가입 성공에 성공한다.")
	void signUpSuccess() {
		willDoNothing().given(userMapper).saveUser(any(User.class));

		userService.signUp(signUpRequest);

		then(userMapper).should().saveUser(any(User.class));
	}

	@Test
	@DisplayName("아이디 중복으로 회원가입에 실패한다.")
	void signUpWithDuplicatedUserId() {
		// given
		DuplicateKeyException exception = new DuplicateKeyException(signUpRequest.getUserId());
		willThrow(exception).given(userMapper).saveUser(any(User.class));

		// when & then
		assertThatThrownBy(() -> userService.signUp(signUpRequest))
			.isInstanceOf(DuplicatedUserIdException.class)
			.hasMessageContaining("중복된 아이디입니다.");
		then(userMapper).should().saveUser(any(User.class));
	}

	@Test
	@DisplayName("이메일 중복으로 회원가입에 실패한다.")
	void signUpWithDuplicatedEmail() {
		// given
		DuplicateKeyException exception = new DuplicateKeyException(signUpRequest.getEmail());
		willThrow(exception).given(userMapper).saveUser(any(User.class));

		// when & then
		assertThatThrownBy(() -> userService.signUp(signUpRequest))
			.isInstanceOf(DuplicatedEmailException.class)
			.hasMessageContaining("중복된 이메일입니다.");
		then(userMapper).should().saveUser(any(User.class));
	}

	@Test
	@DisplayName("휴대폰 번호 중복으로 회원가입에 실패한다.")
	void signUpWitDuplicatedPhone() {
		// given
		DuplicateKeyException exception = new DuplicateKeyException(signUpRequest.getPhone());
		willThrow(exception).given(userMapper).saveUser(any(User.class));

		// when & then
		assertThatThrownBy(() -> userService.signUp(signUpRequest))
			.isInstanceOf(DuplicatedPhoneException.class)
			.hasMessageContaining("중복된 휴대폰 번호입니다.");
		then(userMapper).should().saveUser(any(User.class));
	}

	@Test
	@DisplayName("아이디 중복체크에 성공한다.")
	void checkUserIdDuplicationSuccess() {
		given(userMapper.hasUserId(signUpRequest.getUserId())).willReturn(false);

		userService.checkUserIdDuplication(signUpRequest.getUserId());

		then(userMapper).should().hasUserId(signUpRequest.getUserId());
	}

	@Test
	@DisplayName("아이디 중복으로 아이디 중복체크 실패한다.")
	void checkUserIdDuplicationWithDuplicatedUserId() {
		// given
		given(userMapper.hasUserId(signUpRequest.getUserId())).willReturn(true);

		// when & then
		assertThatThrownBy(() -> userService.checkUserIdDuplication(signUpRequest.getUserId()))
			.isInstanceOf(DuplicatedUserIdException.class)
			.hasMessageContaining("중복된 아이디입니다.");
		then(userMapper).should().hasUserId(signUpRequest.getUserId());
	}
}