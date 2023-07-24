package com.ghm.giftcardfleamarket.user.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.user.domain.User;

@Mapper
public interface UserMapper {

	void saveUser(User user);

	boolean hasUserId(String userId);

	Optional<User> findUserByUserId(String userId);
}
