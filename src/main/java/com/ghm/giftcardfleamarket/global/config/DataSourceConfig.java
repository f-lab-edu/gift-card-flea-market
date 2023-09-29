package com.ghm.giftcardfleamarket.global.config;

import static com.ghm.giftcardfleamarket.global.util.constants.DataSourceType.*;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import com.ghm.giftcardfleamarket.global.util.RoutingDataSource;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {

	private final String MASTER_DATA_SOURCE = "masterDataSource";
	private final String SLAVE_DATA_SOURCE = "slaveDataSource";

	@Value("${mybatis.type-aliases-package}")
	private String typeAliasesPackage;

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari.master")
	public DataSource masterDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari.slave")
	public DataSource slaveDataSource() {
		return DataSourceBuilder.create().type(HikariDataSource.class).build();
	}

	@Bean
	@DependsOn({MASTER_DATA_SOURCE, SLAVE_DATA_SOURCE})
	public DataSource routingDataSource(@Qualifier(MASTER_DATA_SOURCE) DataSource master,
		@Qualifier(SLAVE_DATA_SOURCE) DataSource slave) {

		RoutingDataSource routingDataSource = new RoutingDataSource();

		Map<Object, Object> dataSourceMap = new HashMap<>();
		dataSourceMap.put(MASTER, master);
		dataSourceMap.put(SLAVE, slave);

		routingDataSource.setTargetDataSources(dataSourceMap);
		routingDataSource.setDefaultTargetDataSource(master);

		return routingDataSource;
	}

	@Bean
	@Primary
	@DependsOn("routingDataSource")
	public LazyConnectionDataSourceProxy dataSource(DataSource routingDataSource) {
		return new LazyConnectionDataSourceProxy(routingDataSource);
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();

		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		bean.setTypeAliasesPackage(typeAliasesPackage);

		return bean.getObject();
	}

	@Bean
	public DataSourceTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}