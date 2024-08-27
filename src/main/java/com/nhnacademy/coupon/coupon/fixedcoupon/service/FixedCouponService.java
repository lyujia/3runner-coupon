package com.nhnacademy.coupon.coupon.fixedcoupon.service;

import com.nhnacademy.coupon.coupon.fixedcoupon.dto.response.ReadFixedCouponResponse;

/**
 * 고정 쿠폰 서비스 인터페이스.
 *
 * @author 김병우
 */
public interface FixedCouponService {
    /**
     * 고정쿠폰 정책 생성.
     *
     * @param discountPrice 할인금액
     * @return 쿠폰타입 아이디
     */
    Long create(int discountPrice);

    /**
     * 고정쿠폰 정책 읽기.
     *
     * @param couponTypeId 쿠폰타입 아이디
     * @return 고정쿠폰Dto
     */
    ReadFixedCouponResponse read(Long couponTypeId);
}
