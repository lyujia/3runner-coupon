package com.nhnacademy.coupon.coupon.couponusage.service.impl;

import com.nhnacademy.coupon.coupon.couponusage.dto.response.ReadCouponUsageResponse;
import com.nhnacademy.coupon.coupon.couponusage.repository.CouponUsageRespository;
import com.nhnacademy.coupon.coupon.couponusage.service.CouponUsageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 쿠폰사용처 서비스 구현체.
 *
 * @author 김병우
 */
@Transactional
@RequiredArgsConstructor
@Service
public class CouponUsageServiceImpl implements CouponUsageService {
    private final CouponUsageRespository couponUsageRespository;

    /**
     * 쿠폰 사용처 모두 읽기.
     *
     * @return 반환dto
     */
    @Override
    public List<ReadCouponUsageResponse> readAll() {
        return couponUsageRespository
                .findAll()
                .stream()
                .map(o->ReadCouponUsageResponse.builder()
                        .couponUsageId(o.getId())
                        .usage(o.getUsage())
                        .build())
                .toList();
    }
}
