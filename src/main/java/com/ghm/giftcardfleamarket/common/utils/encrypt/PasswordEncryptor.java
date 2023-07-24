package com.ghm.giftcardfleamarket.common.utils.encrypt;

public interface PasswordEncryptor {

	String encrypt(String password);

	boolean isMatch(String password, String hashedPassword);
}
