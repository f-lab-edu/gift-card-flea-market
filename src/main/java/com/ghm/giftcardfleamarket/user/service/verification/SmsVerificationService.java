package com.ghm.giftcardfleamarket.user.service.verification;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghm.giftcardfleamarket.common.utils.SmsVerificationUtil;
import com.ghm.giftcardfleamarket.user.dao.SmsVerificationDao;
import com.ghm.giftcardfleamarket.user.dto.request.SmsApiRequest;
import com.ghm.giftcardfleamarket.user.dto.request.SmsVerificationRequest;
import com.ghm.giftcardfleamarket.user.dto.response.SmsApiResponse;
import com.ghm.giftcardfleamarket.user.exception.verification.SmsSendFailedException;
import com.ghm.giftcardfleamarket.user.exception.verification.VerificationCodeMisMatchException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmsVerificationService {

	private static final String SERVICE_ID = "ncp:sms:kr:306692833450:flea_market";
	private static final String REQUEST_URL =
		"https://sens.apigw.ntruss.com/sms/v2/services/" + SERVICE_ID + "/messages";

	private final RestTemplate restTemplate;
	private final ObjectMapper objectMapper;
	private final SmsVerificationDao smsCertificationDao;

	public void sendSms(String phone) throws JsonProcessingException {
		String verificationCode = SmsVerificationUtil.makeVerificationCode();
		HttpHeaders headers = SmsVerificationUtil.makeSmsApiRequestHeaders();
		SmsApiRequest body = SmsVerificationUtil.makeSmsApiRequestBody(phone, verificationCode);

		String jsonStrBody = objectMapper.writeValueAsString(body);
		HttpEntity<String> httpRequest = new HttpEntity<>(jsonStrBody, headers);

		SmsApiResponse smsApiResponse = restTemplate.postForObject(REQUEST_URL, httpRequest, SmsApiResponse.class);
		if (smsApiResponse.getStatusName().equals("fail")) {
			throw new SmsSendFailedException(smsApiResponse.getStatusCode());
		}
		smsCertificationDao.saveVerificationCode(phone, verificationCode);
	}

	public void verifyVerificationCode(SmsVerificationRequest smsRequest) {
		String findCode = smsCertificationDao.getVerificationCode(smsRequest.getPhone());
		if (!smsRequest.getVerificationCode().equals(findCode)) {
			throw new VerificationCodeMisMatchException("인증 번호가 일치하지 않습니다.");
		}
		smsCertificationDao.deleteVerificationCode(smsRequest.getPhone());
	}
}
