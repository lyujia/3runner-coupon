package com.nhnacademy.coupon.coupon.couponform.dto.request;

import lombok.Builder;

/**
 * 비율쿠폰Dto.
 *
 * @param discountRate 할인율
 * @param discountMaxPrice 할인최대가
 */
@Builder
public record CreateRatioCouponRequest(double discountRate, int discountMaxPrice) {
}
