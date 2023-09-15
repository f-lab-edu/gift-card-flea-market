package com.ghm.giftcardfleamarket.domain.user.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import com.ghm.giftcardfleamarket.domain.user.domain.User;

@Mapper
public interface UserMapper {

	void insertUser(User user);

	boolean hasUserId(String userId);

	Optional<User> selectUserByUserId(String userId);

	String selectUserIdById(Long id);
}
