package com.ghm.giftcardfleamarket.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.ghm.giftcardfleamarket.user.dto.request.MessageRequest;
import com.ghm.giftcardfleamarket.user.dto.request.SmsApiRequest;

@Component
public class SmsVerificationUtil {

	private static String accessKey;
	private static String secretKey;
	private static String serviceId;
	private static String sender;

	private SmsVerificationUtil() {
	}

	public static String makeVerificationCode() {
		return String.valueOf(new Random().nextInt(900000) + 100000);
	}

	public static HttpHeaders makeSmsApiRequestHeaders() {
		String timestamp = Long.toString(System.currentTimeMillis());

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("x-ncp-apigw-timestamp", timestamp);
		headers.add("x-ncp-iam-access-key", accessKey);
		headers.add("x-ncp-apigw-signature-v2", makeSignature(timestamp));
		return headers;
	}

	public static SmsApiRequest makeSmsApiRequestBody(String phone, String verificationCode) {
		List<MessageRequest> messages = new ArrayList<>();
		messages.add(MessageRequest.builder()
			.to(phone)
			.content(String.format("[기프티콘 플리마켓] 인증 번호 [%s]를 입력해주세요.", verificationCode))
			.build());

		return SmsApiRequest.builder()
			.type("SMS")
			.contentType("COMM")
			.countryCode("82")
			.from(sender)
			.content("기본 메시지")
			.messages(messages)
			.build();
	}

	private static String makeSignature(String timestamp) {
		String space = " ";
		String newLine = "\n";
		String method = "POST";
		String url = "/sms/v2/services/" + serviceId + "/messages";

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");

		Mac mac = null;
		try {
			mac = Mac.getInstance("HmacSHA256");
			mac.init(signingKey);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}

		byte[] rawHmac = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
		return Base64.encodeBase64String(rawHmac);
	}

	@Value("${ncp.access-key}")
	private void setAccessKey(String value) {
		accessKey = value;
	}

	@Value("${ncp.secret-key}")
	private void setSecretKey(String value) {
		secretKey = value;
	}

	@Value("${ncp.service-id}")
	private void setServiceId(String value) {
		serviceId = value;
	}

	@Value("${ncp.sender}")
	private void setSender(String value) {
		sender = value;
	}
}
