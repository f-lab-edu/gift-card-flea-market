package com.ghm.giftcardfleamarket.item.service;

import static com.ghm.giftcardfleamarket.common.utils.PriceCalculationUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.dto.response.ItemListResponse;
import com.ghm.giftcardfleamarket.item.dto.response.ItemResponse;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

	@Mock
	private ItemMapper itemMapper;

	@InjectMocks
	private ItemService itemService;

	private List<Item> itemList;

	private List<ItemResponse> itemResponseList;

	@BeforeEach
	void setUp() {
		itemList = Arrays.asList(
			new Item("카페아메리카노", 4500),
			new Item("카페라떼", 5000),
			new Item("콜드브루", 4900));

		itemResponseList = Arrays.asList(
			ItemResponse.builder()
				.name("카페아메리카노")
				.price(4500)
				.discountPrice(calculatePrice(4500, DISCOUNT_RATE))
				.build(),

			ItemResponse.builder()
				.name("카페라떼")
				.price(5000)
				.discountPrice(calculatePrice(5000, DISCOUNT_RATE))
				.build(),

			ItemResponse.builder()
				.name("카페라떼")
				.price(5000)
				.discountPrice(calculatePrice(5000, DISCOUNT_RATE))
				.build()
		);
	}

	@Test
	@DisplayName("스타벅스 브랜드에 해당하는 아이템 목록을 조회하는데 성공한다.")
	void getItemsByBrandSuccess() {
		given(itemMapper.selectItemsByBrand(1L)).willReturn(itemList);

		ItemListResponse result = itemService.getItemsByBrand(1L);

		then(itemMapper).should().selectItemsByBrand(1L);
		assertEquals(result.getSize(), itemResponseList.size());
		assertEquals(result.getItemResponseList().get(0).getDiscountPrice(),
			itemResponseList.get(0).getDiscountPrice());
	}
}