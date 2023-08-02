package com.ghm.giftcardfleamarket.common.utils.constants;

public enum PageSize {
	BRAND_PAGE_SIZE(9),
	ITEM_PAGE_SIZE(4);

	private final int pageSize;

	PageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}
}
