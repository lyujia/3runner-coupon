package com.nhnacademy.coupon.coupon.couponform.dto;

import lombok.Builder;


/**
 * 쿠폰폼Dto.
 *
 * @param id 아이디
 * @param name 이름
 */
@Builder
public record CouponFormDto(Long id, String name) {
    }
