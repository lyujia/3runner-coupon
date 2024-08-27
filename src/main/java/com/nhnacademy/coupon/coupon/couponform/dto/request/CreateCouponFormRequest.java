package com.nhnacademy.coupon.coupon.couponform.dto.request;

import lombok.Builder;

import java.time.ZonedDateTime;

/**
 * 쿠폰폼 Dto.
 *
 * @param startDate 시작일
 * @param endDate 만료일
 * @param name 이름
 * @param maxPrice 최대가격
 * @param minPrice 최소가격
 * @param couponTypeId 쿠폰타입아이디
 * @param couponUsageId 쿠폰사용처아이디
 */
@Builder
public record CreateCouponFormRequest(
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        String name,
        Integer maxPrice,
        Integer minPrice,
        Long couponTypeId,
        Long couponUsageId) {
}
