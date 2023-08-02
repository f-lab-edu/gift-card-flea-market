package com.ghm.giftcardfleamarket.common.utils;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncryptor implements PasswordEncryptor {

	@Override
	public String encrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	@Override
	public boolean isMatch(String password, String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);
	}
}
