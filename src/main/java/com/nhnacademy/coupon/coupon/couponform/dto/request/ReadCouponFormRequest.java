package com.nhnacademy.coupon.coupon.couponform.dto.request;

import lombok.Builder;

import java.util.List;

/**
 * 쿠폰폼 Dto.
 *
 * @param couponFormIds 쿠폰폼아이디 리스트
 */
@Builder
public record ReadCouponFormRequest(
        List<Long> couponFormIds) {
}
