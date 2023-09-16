package com.ghm.giftcardfleamarket.domain.sale.dto.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.ghm.giftcardfleamarket.domain.sale.domain.Sale;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SaleRequest {

	private Long itemId;

	@NotBlank(message = "바코드 입력을 필수입니다.")
	@Pattern(regexp = "^[0-9]{12}$", message = "바코드 형식에 맞게 입력해 주세요.")
	private String barcode;

	@NotNull(message = "유효기간 입력은 필수입니다.")
	@FutureOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expirationDate;

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Sale toEntity(String loginUserId) {
		return Sale.builder()
			.sellerId(loginUserId)
			.itemId(itemId)
			.barcode(barcode)
			.expirationDate(expirationDate)
			.registeredAt(LocalDateTime.now())
			.build();
	}
}