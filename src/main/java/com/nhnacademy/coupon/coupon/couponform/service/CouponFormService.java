package com.nhnacademy.coupon.coupon.couponform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.entity.couponform.CouponForm;

import java.util.List;

/**
 * 쿠폰 폼 서비스 인터페이스.
 *
 * @author 김병우
 */
public interface CouponFormService {
    /**
     * 쿠폰 폼 생성.
     *
     * @param readCouponFormRequest 요청Dto.
     * @return 쿠폰폼 아이디
     */
    Long create(CreateCouponFormRequest readCouponFormRequest);

    /**
     * 쿠폰 폼 읽기.
     *
     * @param couponFormId 쿠폰폼아이디
     * @return 쿠폰폼.
     */
    CouponForm read(Long couponFormId);

    /**
     * 쿠폰 폼 리스트 읽기.
     *
     * @param couponFormIds 쿠폰폼아이디 리스트
     * @return 쿠폰폼 반환 리스트
     */
    List<ReadCouponFormResponse> readAll(List<Long> couponFormIds);

    /**
     * 쿠폰 만료 알림.
     *
     * @throws JsonProcessingException Json 변환 에러
     */
    void sendNoticeCouponsExpiringThreeDaysLater() throws JsonProcessingException;

    /**
     * 쿠폰폼 전체읽기.
     *
     * @return
     */
    List<ReadCouponFormResponse> readAllForms();

}
