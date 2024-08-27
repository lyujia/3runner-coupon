package com.nhnacademy.coupon.coupon.couponform.dto.request;

import lombok.Builder;

import java.util.List;

/**
 * 북쿠폰생성 Dto.
 *
 * @param bookIds 북아이디리스트
 */
@Builder
public record CreateBookCouponRequest(List<Long> bookIds) {
}
