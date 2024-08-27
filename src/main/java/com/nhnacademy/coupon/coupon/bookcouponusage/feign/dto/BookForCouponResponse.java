package com.nhnacademy.coupon.coupon.bookcouponusage.feign.dto;

import lombok.Builder;

@Builder
public record BookForCouponResponse(long id, String title) {
}
