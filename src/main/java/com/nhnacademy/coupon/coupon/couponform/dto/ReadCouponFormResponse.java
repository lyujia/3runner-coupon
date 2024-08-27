package com.nhnacademy.coupon.coupon.couponform.dto;

import lombok.Builder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 쿠폰폼 Response Dto.
 *
 * @param couponFormId 쿠폰폼 아이디
 * @param startDate 시작일
 * @param endDate 만료일
 * @param createdAt 생성일
 * @param name 이름
 * @param code 코드
 * @param maxPrice 최대가격
 * @param minPrice 최소가격
 * @param couponTypeId 쿠폰타입아이디
 * @param couponUsageId 쿠폰사용처아이디
 * @param type 쿠폰타입 이름
 * @param usage 쿠폰사용처 이름
 * @param books 북아이디 리스트
 * @param categorys 카테고리아이디 리스트
 * @param discountPrice 할인가격
 * @param discountRate 할인율
 * @param discountMax 할인 최대가
 */
@Builder
public record ReadCouponFormResponse(
        Long couponFormId,
        ZonedDateTime startDate,
        ZonedDateTime endDate,
        ZonedDateTime createdAt,
        String name,
        UUID code,
        Integer maxPrice,
        Integer minPrice,
        Long couponTypeId,
        Long couponUsageId,
        String type,
        String usage,
        List<Long> books,
        List<Long> categorys,
        Integer discountPrice,
        Double discountRate,
        Integer discountMax
        ) {
}
