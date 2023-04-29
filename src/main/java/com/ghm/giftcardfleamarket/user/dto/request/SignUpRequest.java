package com.ghm.giftcardfleamarket.user.dto.request;

import com.ghm.giftcardfleamarket.user.domain.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

/**
 * NotBlank: null, 빈 문자열(""), 공백 문자열(" ") 모두 허용 X
 * Email: 빈 문자열과 공백을 허용하지 않지만, null을 허용하기 때문에 @NotNull 추가
 * Pattern: 010-xxxx-xxxx 형식에 부합하는지 확인
 */

@Getter
public class SignUpRequest {

	@NotBlank(message = "아이디 입력은 필수입니다.")
	@Size(min = 6, max = 12, message = "아이디는 6자리 이상 12자 이하여야 합니다.")
	private String userId;

	@NotBlank(message = "비밀번호 입력은 필수입니다.")
	@Size(min = 8, max = 16, message = "비밀번호는 8자 이상 16자 이하여야 합니다.")
	private String password;

	@NotNull(message = "이메일 입력은 필수입니다.")
	@Email(message = "이메일 형식에 맞게 입력해 주세요.")
	private String email;

	@NotBlank(message = "휴대폰 번호 입력은 필수입니다.")
	@Pattern(regexp = "^(010)-\\d{4}-\\d{4}$", message = "휴대폰 번호 형식에 맞게 입력해 주세요.")
	private String phone;

	// SignUpRequest(UserDto)를 Entity(User)에 바인딩하는 메서드
	public User toEntity(String encryptedPassword) {
		return User.builder()
			.userId(userId)
			.password(encryptedPassword)
			.email(email)
			.phone(phone)
			.build();
	}
}
