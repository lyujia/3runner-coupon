package com.nhnacademy.coupon.coupon.categorycouponusage.service;

import java.util.List;

/**
 * 카테고리 쿠폰 사용처 서비스 인터페이스.
 *
 * @author 김병우
 */
public interface CategoryCouponUsageService {
    /**
     * 카테고리 쿠폰 사용처 생성.
     *
     * @param categoryIds 카테고리 아이디 리스트
     * @return 쿠폰 사용처 아이디
     */
    Long create(List<Long> categoryIds);

    /**
     * 사용 가능한 카테고리 반환
     *
     * @param couponUsageId 쿠폰사용처 아이디
     * @return 사용가능한 카테고리 아이디 리스트
     */
    List<Long> readCategorys(Long couponUsageId);
}
