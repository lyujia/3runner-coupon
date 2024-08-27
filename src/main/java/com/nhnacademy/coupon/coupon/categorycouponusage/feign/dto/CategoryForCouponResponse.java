package com.nhnacademy.coupon.coupon.categorycouponusage.feign.dto;

import lombok.Builder;

@Builder
public record CategoryForCouponResponse(long id, String name) {
}
