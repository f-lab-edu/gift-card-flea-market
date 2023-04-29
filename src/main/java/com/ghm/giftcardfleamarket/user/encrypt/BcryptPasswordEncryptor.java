package com.ghm.giftcardfleamarket.user.encrypt;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncryptor implements PasswordEncryptor {

	// 비밀번호(평문)에 salt(임의의 문자열)을 더해 bcrypt 알고리즘으로 해싱한 암호화된 결과값(digest) 리턴합니다.
	@Override
	public String encrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
}
