package com.ghm.giftcardfleamarket.user.service;

public interface LoginService {

	void login(long id);

	void logout();

	Long getLoginUser();
}
