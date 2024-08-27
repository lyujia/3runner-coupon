package com.nhnacademy.coupon.coupon.couponform.dto.request;

import lombok.Builder;

/**
 * 고정쿠폰 Dto.
 *
 * @param discountPrice 할인가격
 */
@Builder
public record CreateFixedCouponRequest(int discountPrice) {
}
