package com.ghm.giftcardfleamarket.domain.user.service;

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
import org.springframework.mock.web.MockHttpSession;

import com.ghm.giftcardfleamarket.domain.user.domain.User;
import com.ghm.giftcardfleamarket.global.util.PasswordEncryptor;
import com.ghm.giftcardfleamarket.global.util.constants.SessionNames;

@ExtendWith(MockitoExtension.class)
class SessionLoginServiceTest {

	@Mock
	private MockHttpSession mockSession;

	@Mock
	private PasswordEncryptor passwordEncryptor;

	@InjectMocks
	private SessionLoginService sessionLoginService;

	private User testUser;

	@BeforeEach
	void setUp() {
		testUser = User.builder()
			.id(1L)
			.userId("testUserId")
			.password(passwordEncryptor.encrypt("testPassword"))
			.build();
	}

	@Test
	@DisplayName("로그인에 성공한다.")
	void loginSuccess() {
		willDoNothing().given(mockSession).setAttribute(SessionNames.LOGIN_USER, testUser.getId());
		given(mockSession.getAttribute(SessionNames.LOGIN_USER)).willReturn(testUser.getId());

		sessionLoginService.login(testUser.getId());

		then(mockSession).should().setAttribute(SessionNames.LOGIN_USER, testUser.getId());
		assertEquals(mockSession.getAttribute(SessionNames.LOGIN_USER), testUser.getId());
	}

	@Test
	@DisplayName("로그아웃에 성공한다.")
	void logoutSuccess() {
		willDoNothing().given(mockSession).removeAttribute(SessionNames.LOGIN_USER);
		mockSession.setAttribute(SessionNames.LOGIN_USER, testUser.getId());

		sessionLoginService.logout();

		then(mockSession).should().removeAttribute(SessionNames.LOGIN_USER);
		assertNull(mockSession.getAttribute(SessionNames.LOGIN_USER));
	}

	@Test
	@DisplayName("세션에 저장된 유저의 고유한 id를 가져오는데 성공한다.")
	void getLoginUserSuccess() {
		given(mockSession.getAttribute(SessionNames.LOGIN_USER)).willReturn(testUser.getId());

		Optional<Long> result = sessionLoginService.getLoginUser();

		then(mockSession).should().getAttribute(SessionNames.LOGIN_USER);
		assertEquals(result.orElse(0L), testUser.getId());
	}
}