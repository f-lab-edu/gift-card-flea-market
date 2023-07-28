package com.ghm.giftcardfleamarket.common.utils;

public class PagingUtil {

	public static final int dataCountPerPage = 4;

	public static int getOffset(int pageNum) {
		return dataCountPerPage * (pageNum - 1);
	}
}
