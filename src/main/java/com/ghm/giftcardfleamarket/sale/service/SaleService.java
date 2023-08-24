package com.ghm.giftcardfleamarket.sale.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.PriceCalculationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.Page.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PriceRate.*;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;
import com.ghm.giftcardfleamarket.sale.domain.Sale;
import com.ghm.giftcardfleamarket.sale.dto.request.SaleRequest;
import com.ghm.giftcardfleamarket.sale.dto.response.SaleListResponse;
import com.ghm.giftcardfleamarket.sale.dto.response.SaleResponse;
import com.ghm.giftcardfleamarket.sale.exception.DuplicatedBarcodeException;
import com.ghm.giftcardfleamarket.sale.mapper.SaleMapper;
import com.ghm.giftcardfleamarket.user.exception.UnauthorizedUserException;
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
		saleMapper.insertSaleGiftCard(saleRequest.toEntity(findLoginUserIdInSession()));
	}

	public SaleListResponse getMySoldGiftCards(int page) {
		Map<String, Object> userIdAndPageInfo = putUserIdAndPageInfoToMap(findLoginUserIdInSession(), page,
			SALE_PAGE_SIZE.getPageSize());
		List<Sale> saleList = saleMapper.selectMySoldGiftCards(userIdAndPageInfo);

		if (CollectionUtils.isEmpty(saleList)) {
			return SaleListResponse.empty();
		}

		List<SaleResponse> saleResponseList = saleList.stream()
			.map(sale -> {
				Item item = itemMapper.selectItemDetails(sale.getItemId());
				return SaleResponse.of(sale, item.getName(), calculatePrice(item.getPrice(), PROPOSAL_RATE.getRate()));
			})
			.toList();

		return new SaleListResponse(saleResponseList);
	}

	private String findLoginUserIdInSession() {
		return loginService.getLoginUser()
			.map(userMapper::selectUserIdById)
			.orElseThrow(() -> new UnauthorizedUserException("로그인 후 이용 가능합니다."));
	}
}