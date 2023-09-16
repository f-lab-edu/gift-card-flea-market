package com.ghm.giftcardfleamarket.domain.user.service;

import java.util.Optional;

public interface LoginService {

	void login(long id);

	void logout();

	Optional<Long> getLoginUser();
}
