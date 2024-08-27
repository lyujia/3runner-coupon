package com.nhnacademy.coupon.coupon.bookcouponusage.service;

import java.util.List;


/**
 * 북 쿠폰 사용처 서비스 인터페이스.
 *
 * @author 김병우
 */
public interface BookCouponUsageService {

    /**
     * 북 쿠폰 사용처 생성.
     *
     * @param bookIds 북아이디 리스트
     * @return 쿠폰사용처아이디
     */
    Long create(List<Long> bookIds);


    /**
     * 북 쿠폰 사용가능한 북 리스트 반환 메소드.
     *
     * @param couponUsageId 쿠폰 사용처 아이디
     * @return 사용처 사용가능한 북 아이디 리스트
     */
    List<Long> readBooks(Long couponUsageId);
}
