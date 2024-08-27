package com.nhnacademy.coupon.coupon.couponform.dto.request;

import lombok.Builder;

import java.util.List;

/**
 * 카테고리쿠폰 Dto.
 *
 * @param categoryIds 카테고리아이디 리스트
 */
@Builder
public record CreateCategoryCouponRequest(List<Long> categoryIds) {
}
