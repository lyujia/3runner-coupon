package com.nhnacademy.coupon.coupon.couponusage.dto.response;

import lombok.Builder;

/**
 * 쿠폰사용처 Dto.
 *
 * @param couponUsageId 쿠폰사용처아이디
 * @param usage 사용처
 */
@Builder
public record ReadCouponUsageResponse(Long couponUsageId, String usage) {
}
