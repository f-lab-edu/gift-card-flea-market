package com.ghm.giftcardfleamarket.domain.user.dto.request;

import java.time.LocalDateTime;

import com.ghm.giftcardfleamarket.domain.user.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

	@NotBlank(message = "아이디 입력은 필수입니다.")
	@Size(min = 6, max = 12, message = "아이디는 {min}자리 이상 {max}자 이하여야 합니다.")
	private String userId;

	@NotBlank(message = "비밀번호 입력은 필수입니다.")
	@Size(min = 8, max = 16, message = "비밀번호는 {min}자 이상 {max}자 이하여야 합니다.")
	private String password;

	@NotNull(message = "이메일 입력은 필수입니다.")
	@Email(message = "이메일 형식에 맞게 입력해 주세요.")
	private String email;

	@NotBlank(message = "휴대폰 번호 입력은 필수입니다.")
	@Pattern(regexp = "^(010|011)[0-9]{8}$", message = "휴대폰 번호 형식에 맞게 입력해 주세요.")
	private String phone;

	public User toEntity(String encryptedPassword) {
		return User.builder()
			.userId(userId)
			.password(encryptedPassword)
			.email(email)
			.phone(phone)
			.createdAt(LocalDateTime.now())
			.build();
	}
}
