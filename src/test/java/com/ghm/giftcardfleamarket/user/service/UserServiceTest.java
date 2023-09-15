package com.ghm.giftcardfleamarket.user.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import com.ghm.giftcardfleamarket.domain.user.domain.User;
import com.ghm.giftcardfleamarket.domain.user.dto.request.LoginRequest;
import com.ghm.giftcardfleamarket.domain.user.dto.request.SignUpRequest;
import com.ghm.giftcardfleamarket.domain.user.exception.DuplicatedEmailException;
import com.ghm.giftcardfleamarket.domain.user.exception.DuplicatedPhoneException;
import com.ghm.giftcardfleamarket.domain.user.exception.DuplicatedUserIdException;
import com.ghm.giftcardfleamarket.domain.user.exception.PasswordMisMatchException;
import com.ghm.giftcardfleamarket.domain.user.exception.UserIdNotFoundException;
import com.ghm.giftcardfleamarket.domain.user.mapper.UserMapper;
import com.ghm.giftcardfleamarket.domain.user.service.UserService;
import com.ghm.giftcardfleamarket.global.util.PasswordEncryptor;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserMapper userMapper;

	@Mock
	private PasswordEncryptor passwordEncryptor;

	@InjectMocks
	private UserService userService;

	private SignUpRequest signUpRequest;

	private LoginRequest loginRequest;

	private User testUser;

	@BeforeEach
	void setUp() {
		signUpRequest = SignUpRequest.builder()
			.userId("testUserId")
			.password("testPassword")
			.email("test@example.com")
			.phone("01012345678")
			.build();

		loginRequest = new LoginRequest("testUserId", "testPassword");

		testUser = User.builder()
			.id(1L)
			.userId("testUserId")
			.password(passwordEncryptor.encrypt("testPassword"))
			.build();
	}

	@Test
	@DisplayName("회원가입에 성공한다.")
	void signUpSuccess() {
		willDoNothing().given(userMapper).insertUser(any(User.class));

		userService.signUp(signUpRequest);

		then(userMapper).should().insertUser(any(User.class));
	}

	@Test
	@DisplayName("아이디 중복으로 회원가입에 실패한다.")
	void signUpWithDuplicatedUserId() {
		// given
		DuplicateKeyException exception = new DuplicateKeyException(signUpRequest.getUserId());
		willThrow(exception).given(userMapper).insertUser(any(User.class));

		// when & then
		assertThatThrownBy(() -> userService.signUp(signUpRequest))
			.isInstanceOf(DuplicatedUserIdException.class)
			.hasMessageContaining("중복된 아이디입니다.");
		then(userMapper).should().insertUser(any(User.class));
	}

	@Test
	@DisplayName("이메일 중복으로 회원가입에 실패한다.")
	void signUpWithDuplicatedEmail() {
		// given
		DuplicateKeyException exception = new DuplicateKeyException(signUpRequest.getEmail());
		willThrow(exception).given(userMapper).insertUser(any(User.class));

		// when & then
		assertThatThrownBy(() -> userService.signUp(signUpRequest))
			.isInstanceOf(DuplicatedEmailException.class)
			.hasMessageContaining("중복된 이메일입니다.");
		then(userMapper).should().insertUser(any(User.class));
	}

	@Test
	@DisplayName("휴대폰 번호 중복으로 회원가입에 실패한다.")
	void signUpWitDuplicatedPhone() {
		// given
		DuplicateKeyException exception = new DuplicateKeyException(signUpRequest.getPhone());
		willThrow(exception).given(userMapper).insertUser(any(User.class));

		// when & then
		assertThatThrownBy(() -> userService.signUp(signUpRequest))
			.isInstanceOf(DuplicatedPhoneException.class)
			.hasMessageContaining("중복된 휴대폰 번호입니다.");
		then(userMapper).should().insertUser(any(User.class));
	}

	@Test
	@DisplayName("아이디 중복체크에 성공한다.")
	void checkUserIdDuplicationSuccess() {
		given(userMapper.hasUserId(signUpRequest.getUserId())).willReturn(false);

		userService.checkUserIdDuplication(signUpRequest.getUserId());

		then(userMapper).should().hasUserId(signUpRequest.getUserId());
	}

	@Test
	@DisplayName("아이디 중복으로 아이디 중복체크에 실패한다.")
	void checkUserIdDuplicationWithDuplicatedUserId() {
		// given
		given(userMapper.hasUserId(signUpRequest.getUserId())).willReturn(true);

		// when & then
		assertThatThrownBy(() -> userService.checkUserIdDuplication(signUpRequest.getUserId()))
			.isInstanceOf(DuplicatedUserIdException.class)
			.hasMessageContaining("중복된 아이디입니다.");
		then(userMapper).should().hasUserId(signUpRequest.getUserId());
	}

	@Test
	@DisplayName("로그인에 성공한다.")
	void loginSuccess() {
		given(userMapper.selectUserByUserId(loginRequest.getUserId())).willReturn(Optional.ofNullable(testUser));
		given(passwordEncryptor.isMatch(loginRequest.getPassword(), testUser.getPassword())).willReturn(true);

		User result = userService.findUser(loginRequest);

		then(userMapper).should().selectUserByUserId(loginRequest.getUserId());
		then(passwordEncryptor).should().isMatch(loginRequest.getPassword(), testUser.getPassword());
		assertEquals(result, testUser);
	}

	@Test
	@DisplayName("등록되지 않은 아이디 기입으로 로그인에 실패한다.")
	void loginWithUnRegisteredUserId() {
		// given
		given(userMapper.selectUserByUserId(loginRequest.getUserId())).willReturn(Optional.empty());

		// when & then
		assertThatThrownBy(() -> userService.findUser(loginRequest))
			.isInstanceOf(UserIdNotFoundException.class)
			.hasMessageContaining("등록되지 않은 아이디입니다.");
		then(userMapper).should().selectUserByUserId(loginRequest.getUserId());
		then(passwordEncryptor).should(never()).isMatch(anyString(), anyString());
	}

	@Test
	@DisplayName("비밀번호 오기입으로 로그인에 실패한다.")
	void loginWithInvalidPassword() {
		// given
		given(userMapper.selectUserByUserId(loginRequest.getUserId())).willReturn(Optional.ofNullable(testUser));
		given(passwordEncryptor.isMatch(loginRequest.getPassword(), testUser.getPassword())).willReturn(false);

		// when & then
		assertThatThrownBy(() -> userService.findUser(loginRequest))
			.isInstanceOf(PasswordMisMatchException.class)
			.hasMessageContaining("비밀번호가 일치하지 않습니다.");
		then(userMapper).should().selectUserByUserId(loginRequest.getUserId());
		then(passwordEncryptor).should().isMatch(loginRequest.getPassword(), testUser.getPassword());
	}
}