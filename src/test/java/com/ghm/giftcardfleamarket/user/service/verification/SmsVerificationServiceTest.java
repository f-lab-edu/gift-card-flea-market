package com.ghm.giftcardfleamarket.user.service.verification;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghm.giftcardfleamarket.common.utils.SmsVerificationUtil;
import com.ghm.giftcardfleamarket.user.dao.SmsVerificationDao;
import com.ghm.giftcardfleamarket.user.dto.request.SmsVerificationRequest;
import com.ghm.giftcardfleamarket.user.dto.response.SmsApiResponse;
import com.ghm.giftcardfleamarket.user.exception.verification.VerificationCodeMisMatchException;
import com.ghm.giftcardfleamarket.user.exception.verification.VerificationCodeTimeOutException;

@AutoConfigureWebClient(registerRestTemplate = true)
@RestClientTest(components = {SmsVerificationService.class, SmsVerificationUtil.class})
public class SmsVerificationServiceTest {

	@Autowired
	private SmsVerificationService smsVerificationService;

	@MockBean
	private SmsVerificationDao smsVerificationDao;

	@Autowired
	private MockRestServiceServer mockServer;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${ncp.request-url}")
	private String requestUrl;

	@Test
	@DisplayName("인증번호 문자 발송에 성공한다.")
	void sendSmsSuccess() throws JsonProcessingException {
		// given
		SmsApiResponse smsApiResponse = new SmsApiResponse("202", "success");
		String smsApiResponseJsonStrBody = objectMapper.writeValueAsString(smsApiResponse);

		mockServer
			.expect(requestTo(requestUrl))
			.andExpect(method(HttpMethod.POST))
			.andRespond(withSuccess(smsApiResponseJsonStrBody, MediaType.APPLICATION_JSON));

		// when
		smsVerificationService.sendSms("01012345678");

		// then
		mockServer.verify();
	}

	@Test
	@DisplayName("휴대폰 번호, 인증번호 일치로 인증에 성공한다.")
	void verifyVerificationCodeSuccess() {
		given(smsVerificationDao.getVerificationCode("01012345678")).willReturn("111111");
		SmsVerificationRequest smsRequest = new SmsVerificationRequest("01012345678", "111111");

		smsVerificationService.verifyVerificationCode(smsRequest);

		then(smsVerificationDao).should().getVerificationCode(smsRequest.getPhone());
		then(smsVerificationDao).should().deleteVerificationCode(smsRequest.getPhone());
	}

	@Test
	@DisplayName("인증번호 불일치로 인증에 실패한다.")
	void verifyWithInvalidVerificationCode() {
		// given
		given(smsVerificationDao.getVerificationCode("01012345678")).willReturn("111111");
		SmsVerificationRequest smsRequest = new SmsVerificationRequest("01012345678", "222222");

		// when & then
		assertThatThrownBy(() -> smsVerificationService.verifyVerificationCode(smsRequest))
			.isInstanceOf(VerificationCodeMisMatchException.class)
			.hasMessageContaining("인증번호가 일치하지 않습니다.");
		then(smsVerificationDao).should().getVerificationCode(smsRequest.getPhone());
		then(smsVerificationDao).should(never()).deleteVerificationCode(smsRequest.getPhone());
	}

	@Test
	@DisplayName("3분이 지나 만료된 인증번호를 입력해 인증에 실패한다.")
	void verifyWithTimeOutVerificationCode() {
		// given
		SmsVerificationRequest smsRequest = new SmsVerificationRequest("01012345678", "111111");
		given(smsVerificationDao.getVerificationCode(smsRequest.getPhone())).willReturn(null);

		// when & then
		assertThatThrownBy(() -> smsVerificationService.verifyVerificationCode(smsRequest))
			.isInstanceOf(VerificationCodeTimeOutException.class)
			.hasMessageContaining("인증번호가 만료되었습니다.");
		then(smsVerificationDao).should().getVerificationCode(smsRequest.getPhone());
		then(smsVerificationDao).should(never()).deleteVerificationCode(smsRequest.getPhone());
	}
}

