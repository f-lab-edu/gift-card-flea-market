package com.ghm.giftcardfleamarket.global.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.domain.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.domain.item.domain.Item;
import com.ghm.giftcardfleamarket.domain.item.exception.ItemNotFoundException;
import com.ghm.giftcardfleamarket.domain.item.mapper.ItemMapper;
import com.ghm.giftcardfleamarket.domain.user.exception.UnauthorizedUserException;
import com.ghm.giftcardfleamarket.domain.user.mapper.UserMapper;
import com.ghm.giftcardfleamarket.domain.user.service.LoginService;
import com.ghm.giftcardfleamarket.global.common.model.ItemBrandPair;

@Service
public class CommonService {

	protected ItemMapper itemMapper;
	private final BrandMapper brandMapper;
	private final LoginService loginService;
	private final UserMapper userMapper;

	@Autowired
	public CommonService(ItemMapper itemMapper, BrandMapper brandMapper, LoginService loginService,
		UserMapper userMapper) {
		this.itemMapper = itemMapper;
		this.brandMapper = brandMapper;
		this.loginService = loginService;
		this.userMapper = userMapper;
	}

	public ItemBrandPair getItemAndBrandName(Long itemId) {
		Item item = itemMapper.selectItemDetails(itemId)
			.orElseThrow(() -> new ItemNotFoundException(itemId));
		String brandName = brandMapper.selectBrandName(item.getBrandId());

		return new ItemBrandPair(item, brandName);
	}

	public String findLoginUserIdInSession() {
		return loginService.getLoginUser()
			.map(userMapper::selectUserIdById)
			.orElseThrow(() -> new UnauthorizedUserException("로그인 후 이용 가능합니다."));
	}
}