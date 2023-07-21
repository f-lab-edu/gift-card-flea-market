package com.ghm.giftcardfleamarket.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ghm.giftcardfleamarket.common.interceptor.LoginCheckInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

	private final LoginCheckInterceptor loginCheckInterceptor;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor)
			.order(1)
			.addPathPatterns("/**")
			.excludePathPatterns("/**/singUp", "/**/check-duplication/*",
				"/**/sms-verification", "/**/sms-verification/*", "/**/login");
	}
}
