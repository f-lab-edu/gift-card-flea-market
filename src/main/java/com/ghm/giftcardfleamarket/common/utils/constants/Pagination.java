package com.ghm.giftcardfleamarket.common.utils.constants;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public enum Pagination {
	BRAND_PAGE_SIZE(9),
	ITEM_PAGE_SIZE(4);

	private final int pageSize;

	Pagination(int pageSize) {
		this.pageSize = pageSize;
	}

	public static Map<String, Object> putIdAndPageInfoToMap(Long id, int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return Map.ofEntries(
			Map.entry("id", id),
			Map.entry("pageSize", pageSize),
			Map.entry("offset", pageable.getOffset()));
	}

	public int getPageSize() {
		return pageSize;
	}
}
