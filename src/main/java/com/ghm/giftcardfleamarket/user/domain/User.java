package com.ghm.giftcardfleamarket.user.domain;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {
	private final Long id;
	private final String userId;
	private final String password;
	private final String email;
	private final String phone;
	private final boolean isActive;
	private final LocalDateTime createdAt;
	private final LocalDateTime updatedAt;
	private final LocalDateTime deletedAt;
}
