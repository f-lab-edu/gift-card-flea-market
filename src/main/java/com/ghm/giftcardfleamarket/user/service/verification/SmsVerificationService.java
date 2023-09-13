package com.ghm.giftcardfleamarket.user.service.verification;

import static com.ghm.giftcardfleamarket.common.utils.SmsVerificationUtil.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghm.giftcardfleamarket.user.dao.SmsVerificationDao;
import com.ghm.giftcardfleamarket.user.dto.request.SmsApiRequest;
import com.ghm.giftcardfleamarket.user.dto.request.SmsVerificationRequest;
import com.ghm.giftcardfleamarket.user.dto.response.SmsApiResponse;
import com.ghm.giftcardfleamarket.user.exception.verification.SmsSendFailedException;
import com.ghm.giftcardfleamarket.user.exception.verification.VerificationCodeMisMatchException;
import com.ghm.giftcardfleamarket.user.exception.verification.VerificationCodeTimeOutException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmsVerificationService {

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	private final SmsVerificationDao smsVerificationDao;

	@Value("${ncp.request-url}")
	private String requestUrl;

	public void sendSms(String phone) throws JsonProcessingException {
		String verificationCode = makeVerificationCode();
		HttpHeaders headers = makeSmsApiRequestHeaders();
		SmsApiRequest body = makeSmsApiRequestBody(phone, verificationCode);

		String jsonStrBody = objectMapper.writeValueAsString(body);
		HttpEntity<String> httpRequest = new HttpEntity<>(jsonStrBody, headers);

		SmsApiResponse smsApiResponse = restTemplate.postForObject(requestUrl, httpRequest, SmsApiResponse.class);
		if (smsApiResponse != null && smsApiResponse.getStatusName().equals("fail")) {
			throw new SmsSendFailedException(smsApiResponse.getStatusCode());
		}
		smsVerificationDao.saveVerificationCode(phone, verificationCode);
	}

	public void verifyVerificationCode(SmsVerificationRequest smsRequest) {
		String findCode = smsVerificationDao.getVerificationCode(smsRequest.getPhone())
			.orElseThrow(() -> new VerificationCodeTimeOutException("인증번호가 만료되었습니다."));

		if (!findCode.equals(smsRequest.getVerificationCode())) {
			throw new VerificationCodeMisMatchException("인증번호가 일치하지 않습니다.");
		}
		smsVerificationDao.deleteVerificationCode(smsRequest.getPhone());
	}
}
