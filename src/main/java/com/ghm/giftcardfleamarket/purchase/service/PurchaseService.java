package com.ghm.giftcardfleamarket.purchase.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.purchase.domain.Purchase;
import com.ghm.giftcardfleamarket.purchase.dto.request.PurchaseRequest;
import com.ghm.giftcardfleamarket.purchase.mapper.PurchaseMapper;
import com.ghm.giftcardfleamarket.sale.mapper.SaleMapper;
import com.ghm.giftcardfleamarket.user.exception.UnauthorizedUserException;
import com.ghm.giftcardfleamarket.user.mapper.UserMapper;
import com.ghm.giftcardfleamarket.user.service.LoginService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseService {

	private final PurchaseMapper purchaseMapper;
	private final SaleMapper saleMapper;
	private final UserMapper userMapper;
	private final LoginService loginService;

	public void buyGiftCard(PurchaseRequest purchaseRequest) {
		Purchase purchase = purchaseRequest.toEntity(findLoginUserIdInSession());
		purchaseMapper.insertPurchaseGiftCard(purchase);

		Map<String, Object> saleIdAndPurchaseStatus = Map.ofEntries(
			Map.entry("saleId", purchase.getSaleId()),
			Map.entry("purchaseStatus", 1));
		saleMapper.updatePurchaseStatus(saleIdAndPurchaseStatus);
	}

	private String findLoginUserIdInSession() {
		return loginService.getLoginUser()
			.map(userMapper::selectUserIdById)
			.orElseThrow(() -> new UnauthorizedUserException("로그인 후 이용 가능합니다."));
	}
}