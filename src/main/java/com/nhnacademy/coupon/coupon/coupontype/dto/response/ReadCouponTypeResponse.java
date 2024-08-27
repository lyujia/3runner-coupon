package com.nhnacademy.coupon.coupon.coupontype.dto.response;

import lombok.Builder;

/**
 * 쿠폰타입 Dto.
 *
 * @param couponTypeId 쿠폰타입 아이디
 * @param type 타입이름
 */
@Builder
public record ReadCouponTypeResponse(Long couponTypeId, String type) {
}
