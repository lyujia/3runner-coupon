package com.nhnacademy.coupon.coupon.ratiocoupon.dto.response;

import lombok.Builder;

/**
 * 비율쿠폰 읽기 Dto.
 *
 * @param ratioCouponId 비율쿠폰아이디
 * @param couponTypeId 쿠폰타입아이디
 * @param discountRate 할인율
 * @param discountMaxPrice 할인최대금액
 */
@Builder
public record ReadRatioCouponResponse(
        Long ratioCouponId,
        Long couponTypeId,
        double discountRate,
        int discountMaxPrice) {
}
