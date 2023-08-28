package com.ghm.giftcardfleamarket.item.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.PriceCalculationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.Page.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PriceRate.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ghm.giftcardfleamarket.brand.mapper.BrandMapper;
import com.ghm.giftcardfleamarket.item.domain.Item;
import com.ghm.giftcardfleamarket.item.dto.response.ItemDetailResponse;
import com.ghm.giftcardfleamarket.item.dto.response.ItemListResponse;
import com.ghm.giftcardfleamarket.item.dto.response.ItemResponse;
import com.ghm.giftcardfleamarket.item.exception.ItemNotFoundException;
import com.ghm.giftcardfleamarket.item.mapper.ItemMapper;
import com.ghm.giftcardfleamarket.sale.dto.response.SaleOptionResponse;
import com.ghm.giftcardfleamarket.sale.exception.SaleOptionListNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemMapper itemMapper;
	private final BrandMapper brandMapper;

	public ItemListResponse getItemsByBrand(Long brandId, int page) {
		List<ItemResponse> itemResponseList = new ArrayList<>();

		Map<String, Object> brandIdAndPageInfo = makePagingQueryParamsWithMap(brandId, page,
			ITEM_PAGE_SIZE.getPageSize());
		List<Item> itemList = itemMapper.selectItemsByBrand(brandIdAndPageInfo);
		itemList.forEach(item -> itemResponseList.add(ItemResponse.of(item)));

		return new ItemListResponse(itemMapper.selectItemTotalCountByBrand(brandId), itemResponseList);
	}

	public ItemDetailResponse getItemDetails(Long itemId) {
		return itemMapper.selectItemDetails(itemId)
			.map(item -> {
				String brandName = brandMapper.selectBrandName(item.getBrandId());
				return ItemDetailResponse.of(item, brandName);
			})
			.orElseThrow(() -> new ItemNotFoundException(itemId + "에 해당하는 상품이 없습니다."));
	}

	public SaleOptionResponse getItemNamesByBrand(Long brandId) {
		Map<String, Object> idToBrandId = Map.of("id", brandId);
		List<Item> itemList = itemMapper.selectItemsByBrand(idToBrandId);

		if (CollectionUtils.isEmpty(itemList)) {
			throw new SaleOptionListNotFoundException("아이템 목록을 찾을 수 없습니다.");
		}

		return SaleOptionResponse.ofItemList(itemList);
	}

	public SaleOptionResponse getItemProposalPrice(Long itemId) {
		return itemMapper.selectItemDetails(itemId)
			.map(item -> new SaleOptionResponse(calculatePrice(item.getPrice(), PROPOSAL_RATE.getRate())))
			.orElseThrow(() -> new ItemNotFoundException(itemId + "에 해당하는 상품이 없습니다."));
	}
}
