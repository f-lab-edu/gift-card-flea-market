package com.ghm.giftcardfleamarket.user.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MessageRequest {
	private final String to;
	private final String content;
}
