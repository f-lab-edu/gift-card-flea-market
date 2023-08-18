package com.ghm.giftcardfleamarket.sale.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.PriceCalculationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.Page.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PriceRate.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;
import com.ghm.giftcardfleamarket.sale.domain.Sale;
import com.ghm.giftcardfleamarket.sale.dto.request.SaleRequest;
import com.ghm.giftcardfleamarket.sale.dto.response.SaleListResponse;
import com.ghm.giftcardfleamarket.sale.dto.response.SaleResponse;
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
	private final ItemMapper itemMapper;
	private final LoginService loginService;

	public void sellGiftCard(SaleRequest saleRequest) {
		if (saleMapper.hasBarcode(saleRequest.getBarcode())) {
			throw new DuplicatedBarcodeException("이미 등록된 바코드입니다.");
		}
		saleMapper.insertGiftCard(saleRequest.toEntity(findLoginUserIdInSession()));
	}

	public SaleListResponse getMySoldGiftCards(int page) {
		List<SaleResponse> saleResponseList = new ArrayList<>();

		Map<String, Object> userIdAndPageInfo = putUserIdAndPageInfoToMap(findLoginUserIdInSession(), page,
			SALE_PAGE_SIZE.getPageSize());

		List<Sale> saleList = saleMapper.selectMySoldGiftCards(userIdAndPageInfo);
		saleList.forEach(sale -> {
			Item item = itemMapper.selectItemDetails(sale.getItemId());
			saleResponseList.add(
				SaleResponse.of(sale, item.getName(), calculatePrice(item.getPrice(), PROPOSAL_RATE.getRate())));
		});

		return new SaleListResponse(saleResponseList);
	}

	private String findLoginUserIdInSession() {
		Optional<Long> loginUser = loginService.getLoginUser();
		return userMapper.selectUserIdById(loginUser.get());
	}
}