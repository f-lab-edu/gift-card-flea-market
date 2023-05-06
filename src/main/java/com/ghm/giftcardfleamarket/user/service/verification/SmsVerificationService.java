package com.ghm.giftcardfleamarket.user.service.verification;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghm.giftcardfleamarket.user.dao.SmsVerificationDao;
import com.ghm.giftcardfleamarket.user.dto.request.MessageRequest;
import com.ghm.giftcardfleamarket.user.dto.request.SmsApiRequest;
import com.ghm.giftcardfleamarket.user.dto.request.SmsVerificationRequest;
import com.ghm.giftcardfleamarket.user.dto.response.SmsApiResponse;
import com.ghm.giftcardfleamarket.user.exception.verification.SmsSendFailedException;
import com.ghm.giftcardfleamarket.user.exception.verification.VerificationCodeMisMatchException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SmsVerificationService {

	private static final String ACCESS_KEY = "iJPkzp82Zq6cU4XgmuQv";
	private static final String SECRET_KEY = "Rtfhw7caZMJyOksfnRcVTH897dhi7ndAnbx62Oei";
	private static final String SERVICE_ID = "ncp:sms:kr:306692833450:flea_market";
	private static final String SENDER = "01024008614";
	private static final String REQUEST_URL =
		"https://sens.apigw.ntruss.com/sms/v2/services/" + SERVICE_ID + "/messages";

	private final RestTemplate restTemplate;
	private final SmsVerificationDao smsCertificationDao;

	public void sendSms(String phone) throws JsonProcessingException {
		HttpHeaders headers = makeSmsApiRequestHeaders();
		String verificationCode = makeVerificationCode();
		SmsApiRequest body = makeSmsApiRequestBody(phone, verificationCode);

		String jsonStrBody = new ObjectMapper().writeValueAsString(body);
		HttpEntity<String> httpRequest = new HttpEntity<>(jsonStrBody, headers);

		SmsApiResponse smsApiResponse = restTemplate.postForObject(REQUEST_URL, httpRequest, SmsApiResponse.class);
		if (smsApiResponse.getStatusName().equals("fail")) {
			throw new SmsSendFailedException("SMS 전송에 실패하였습니다.");
		}
		smsCertificationDao.saveVerificationCode(phone, verificationCode);
	}

	private HttpHeaders makeSmsApiRequestHeaders() {
		String timestamp = Long.toString(System.currentTimeMillis());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("x-ncp-apigw-timestamp", timestamp);
		headers.add("x-ncp-iam-access-key", ACCESS_KEY);
		headers.add("x-ncp-apigw-signature-v2", makeSignature(timestamp));
		return headers;
	}

	private static String makeVerificationCode() {
		return String.valueOf(new Random().nextInt(900000) + 100000);
	}

	private SmsApiRequest makeSmsApiRequestBody(String phone, String verificationCode) {
		List<MessageRequest> messages = new ArrayList<>();
		messages.add(MessageRequest.builder()
			.to(phone)
			.content(String.format("[기프티콘 플리마켓] 인증 번호 [%s]를 입력해주세요.", verificationCode))
			.build());

		return SmsApiRequest.builder()
			.type("SMS")
			.contentType("COMM")
			.countryCode("82")
			.from(SENDER)
			.content("기본 메시지")
			.messages(messages)
			.build();
	}

	public void verifyVerificationCode(SmsVerificationRequest smsRequest) {
		String findCode = smsCertificationDao.getVerificationCode(smsRequest.getPhone());
		if (!smsRequest.getVerificationCode().equals(findCode)) {
			throw new VerificationCodeMisMatchException("인증 번호가 일치하지 않습니다.");
		}
		smsCertificationDao.deleteVerificationCode(smsRequest.getPhone());
	}

	public String makeSignature(String timestamp) {
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String url = "/sms/v2/services/" + SERVICE_ID + "/messages";

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(ACCESS_KEY)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

		Mac mac = null;
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}

		byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

		return encodeBase64String;
	}
}
