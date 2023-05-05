package com.ghm.giftcardfleamarket.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SmsVerificationRequest {

	@NotBlank(message = "휴대폰 번호 입력은 필수입니다.")
	@Pattern(regexp = "^(010|011)[0-9]{8}$", message = "휴대폰 번호 형식에 맞게 입력해 주세요.")
	private String phone;

	@NotBlank(message = "인증 번호 입력은 필수입니다.")
	@Size(min = 6, max = 6, message = "인증 번호는 {mix}자리 입니다.")
	private String verificationCode;
}
