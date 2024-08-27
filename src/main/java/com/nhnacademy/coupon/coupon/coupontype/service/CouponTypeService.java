package com.nhnacademy.coupon.coupon.coupontype.service;

import com.nhnacademy.coupon.coupon.coupontype.dto.response.ReadCouponTypeResponse;

import java.util.List;


/**
 * 쿠폰타입 서비스 인터페이스.
 *
 * @author 김병우
 */
public interface CouponTypeService {

    /**
     * 전체 쿠폰타입 반환
     *
     * @return 반환Dto
     */
    List<ReadCouponTypeResponse> readAll();
}
