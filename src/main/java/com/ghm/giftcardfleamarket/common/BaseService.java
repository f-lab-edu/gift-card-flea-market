package com.ghm.giftcardfleamarket.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.common.model.ItemBrandPair;
import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.exception.ItemNotFoundException;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;
import com.ghm.giftcardfleamarket.user.exception.UnauthorizedUserException;
import com.ghm.giftcardfleamarket.user.mapper.UserMapper;
import com.ghm.giftcardfleamarket.user.service.LoginService;

@Service
public class BaseService {

	protected ItemMapper itemMapper;
	private final BrandMapper brandMapper;
	private final LoginService loginService;
	private final UserMapper userMapper;

	@Autowired
	public BaseService(ItemMapper itemMapper, BrandMapper brandMapper, LoginService loginService,
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