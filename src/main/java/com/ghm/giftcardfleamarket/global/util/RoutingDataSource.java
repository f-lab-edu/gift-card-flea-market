package com.ghm.giftcardfleamarket.global.util;

import static com.ghm.giftcardfleamarket.global.util.constants.DataSourceType.*;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? MASTER : SLAVE;
	}
}