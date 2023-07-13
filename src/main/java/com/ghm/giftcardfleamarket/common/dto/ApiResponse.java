package com.ghm.giftcardfleamarket.common.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApiResponse {
	public boolean success;
	public Object data;
	public String message;
	public String statusCode;
}
