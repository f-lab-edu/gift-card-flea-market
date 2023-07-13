package com.ghm.giftcardfleamarket.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SmsApiResponse {
	private String requestId;
	private String requestTime;
	private String statusCode;
	private String statusName;

	public SmsApiResponse(String statusCode, String statusName) {
		this.statusCode = statusCode;
		this.statusName = statusName;
	}
}
