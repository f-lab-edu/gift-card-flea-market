package com.ghm.giftcardfleamarket.common.utils;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationUtil {

	public static Map<String, Object> putIdAndPageInfoToMap(Long id, int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return Map.ofEntries(
			Map.entry("id", id),
			Map.entry("pageSize", pageSize),
			Map.entry("offset", pageable.getOffset()));
	}
}
