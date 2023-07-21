package com.ghm.giftcardfleamarket.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ghm.giftcardfleamarket.user.exception.UnauthorizedUserException;
import com.ghm.giftcardfleamarket.user.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

	private final LoginService loginService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		log.info("로그인 체크 인터셉터 실행");

		if (loginService.getLoginUser() == null) {
			throw new UnauthorizedUserException("로그인 후 이용 가능합니다.");
		}

		return true;
	}
}
