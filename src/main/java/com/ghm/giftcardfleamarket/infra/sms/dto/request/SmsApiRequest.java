package com.ghm.giftcardfleamarket.infra.sms.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SmsApiRequest {
	private final String type;
	private final String contentType;
	private final String countryCode;
	private final String from;
	private final String content;
	private final List<MessageRequest> messages;
}
