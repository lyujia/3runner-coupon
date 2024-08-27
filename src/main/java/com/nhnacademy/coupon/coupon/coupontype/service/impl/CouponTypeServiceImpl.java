package com.nhnacademy.coupon.coupon.coupontype.service.impl;

import com.nhnacademy.coupon.coupon.coupontype.dto.response.ReadCouponTypeResponse;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.coupon.coupontype.service.CouponTypeService;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 쿠폰타입 서비스 구현체.
 *
 * @author 김병우
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CouponTypeServiceImpl implements CouponTypeService {
    private final CouponTypeRepository couponTypeRepository;

    @Override
    public List<ReadCouponTypeResponse> readAll() {
        return couponTypeRepository.findAll().stream()
                .map(this::convertToResponse)
                .toList();
    }

    private ReadCouponTypeResponse convertToResponse(CouponType couponType) {
        return ReadCouponTypeResponse.builder()
                .couponTypeId(couponType.getId())
                .type(couponType.getType())
                .build();
    }
}
