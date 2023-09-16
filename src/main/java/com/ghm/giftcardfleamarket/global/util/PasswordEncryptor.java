package com.ghm.giftcardfleamarket.global.util;

public interface PasswordEncryptor {

	String encrypt(String password);

	boolean isMatch(String password, String hashedPassword);
}
