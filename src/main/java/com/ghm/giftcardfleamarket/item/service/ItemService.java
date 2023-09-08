package com.ghm.giftcardfleamarket.item.service;

import static com.ghm.giftcardfleamarket.common.utils.PaginationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.PriceCalculationUtil.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PageSize.*;
import static com.ghm.giftcardfleamarket.common.utils.constants.PriceRate.*;

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
		int itemTotalCount = itemMapper.selectItemTotalCountByBrand(brandId);

		Map<String, Object> brandIdAndPageInfo = makePagingQueryParamsWithMap(brandId, page, ITEM.getValue());
		List<Item> itemList = itemMapper.selectItemsByBrand(brandIdAndPageInfo);

		List<ItemResponse> itemResponseList = itemList.stream()
			.map(ItemResponse::of)
			.toList();

		return new ItemListResponse(itemTotalCount, itemResponseList);
	}

	public ItemDetailResponse getItemDetails(Long itemId) {
		return itemMapper.selectItemDetails(itemId)
			.map(item -> {
				String brandName = brandMapper.selectBrandName(item.getBrandId());
				return ItemDetailResponse.of(item, brandName);
			})
			.orElseThrow(() -> new ItemNotFoundException(itemId));
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
			.map(item -> new SaleOptionResponse(calculatePrice(item.getPrice(), PROPOSAL.getValue())))
			.orElseThrow(() -> new ItemNotFoundException(itemId));
	}
}
