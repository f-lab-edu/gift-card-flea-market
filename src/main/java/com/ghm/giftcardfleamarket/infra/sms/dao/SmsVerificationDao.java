package com.ghm.giftcardfleamarket.infra.sms.dao;

import java.time.Duration;
import java.util.Optional;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SmsVerificationDao {

	private final String PREFIX = "sms:";
	private final long TIMEOUT_IN_MINUTE = 3L;

	private final RedisTemplate<String, Object> redisTemplate;

	public void saveVerificationCode(String phone, String verificationCode) {
		redisTemplate.opsForValue().set(PREFIX + phone, verificationCode, Duration.ofMinutes(TIMEOUT_IN_MINUTE));
	}

	public Optional<Object> getVerificationCode(String phone) {
		return Optional.ofNullable(redisTemplate.opsForValue().get(PREFIX + phone));
	}

	public void deleteVerificationCode(String phone) {
		redisTemplate.delete(PREFIX + phone);
	}
}
