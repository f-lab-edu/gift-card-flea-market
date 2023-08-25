package com.ghm.giftcardfleamarket.common.utils;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PaginationUtil {

	public static Map<String, Object> makePagingQueryParamsWithMap(Object obj, int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return Map.ofEntries(
			Map.entry(determineMapKeyByType(obj), obj),
			Map.entry("pageSize", pageSize),
			Map.entry("offset", pageable.getOffset()));
	}

	private static String determineMapKeyByType(Object obj) {
		if (obj instanceof Long) {
			return "id";
		}
		if (obj instanceof String) {
			return "userId";
		}

		throw new IllegalArgumentException("지원하지 않는 타입입니다 : " + obj.getClass().getName());
	}
}
