package com.nhnacademy.shoppingmall.domain.order.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class OrderForm {
    @Size(min = 2, max = 100, message = "주소는 최소 3글자 이상 100자 이하로 입력해주세요!")
    private final String address;
    @Size(min = 2, max = 100, message = "이름은 최소 3글자 이상 100자 이하로 입력해주세요!")
    @NotBlank
    private final String addressee;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식에 맞게 입력해주세요")
    private final String phone;
    @NotBlank
    private final String comment;
    private final LocalDateTime rDate;
}
