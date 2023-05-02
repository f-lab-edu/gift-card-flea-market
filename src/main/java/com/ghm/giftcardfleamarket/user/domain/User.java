package com.ghm.giftcardfleamarket.user.domain;

import lombok.Builder;
import lombok.Getter;

/**
 * UserDto에 담긴 데이터를 해당 도메인 객체로 바인딩할 때, 빌더 패턴을 사용합니다. (setter 메서드 지양)
 *
 * 빌더 패턴의 장점
 * 	1. 불변성(immutable) 보장 : 빌더 클래스를 이용해 내부적으로 필요한 데이터를 저장한 후 불변 객체을 생성합니다.
 * 	2. 가독성 : 매개변수가 많은 생성자를 대신해 사용되어 가독성을 높이고, 생성자에 선언된 매개변수 순서를 지켜 작성하지 않아도 됩니다.
 *
 * 빌더 패턴으로 생성된 객체의 불변하는 특성을 고려해, final 키워드를 사용했습니다.
 */

@Getter
@Builder
public class User {
	private final String userId;
	private final String password;
	private final String email;
	private final String phone;
}
