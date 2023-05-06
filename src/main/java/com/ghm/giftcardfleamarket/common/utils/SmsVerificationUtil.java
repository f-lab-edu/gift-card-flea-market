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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.ghm.giftcardfleamarket.user.dto.request.MessageRequest;
import com.ghm.giftcardfleamarket.user.dto.request.SmsApiRequest;

public class SmsVerificationUtil {

	private static final String ACCESS_KEY = "iJPkzp82Zq6cU4XgmuQv";
	private static final String SECRET_KEY = "Rtfhw7caZMJyOksfnRcVTH897dhi7ndAnbx62Oei";
	private static final String SERVICE_ID = "ncp:sms:kr:306692833450:flea_market";
	private static final String SENDER = "01024008614";

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
		headers.add("x-ncp-iam-access-key", ACCESS_KEY);
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
			.from(SENDER)
			.content("기본 메시지")
			.messages(messages)
			.build();
	}

	public static String makeSignature(String timestamp) {
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
