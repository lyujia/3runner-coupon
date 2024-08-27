package com.nhnacademy.coupon.coupon.fixedcoupon.dto.response;

import lombok.Builder;

/**
 * 고정 쿠폰 Dto.
 *
 * @param fixedCouponId 고정쿠폰아이디
 * @param couponTypeId 쿠폰타입아이디
 * @param discountPrice 할인가격
 */
@Builder
public record ReadFixedCouponResponse(
        Long fixedCouponId,
        Long couponTypeId,
        int discountPrice) {
}
