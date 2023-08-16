package com.ghm.giftcardfleamarket.sale.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.sale.dto.request.SaleRequest;
import com.ghm.giftcardfleamarket.sale.exception.DuplicatedBarcodeException;
import com.ghm.giftcardfleamarket.sale.mapper.SaleMapper;
import com.ghm.giftcardfleamarket.user.mapper.UserMapper;
import com.ghm.giftcardfleamarket.user.service.LoginService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

	private final SaleMapper saleMapper;
	private final UserMapper userMapper;
	private final LoginService loginService;

	public void saleGiftCard(SaleRequest saleRequest) {
		if (saleMapper.hasBarcode(saleRequest.getBarcode())) {
			throw new DuplicatedBarcodeException("이미 등록된 바코드입니다.");
		}

		Optional<Long> loginUser = loginService.getLoginUser();
		String loginUserId = userMapper.selectUserIdById(loginUser.get());

		saleMapper.insertSaleGiftCard(saleRequest.toEntity(loginUserId));
	}
}