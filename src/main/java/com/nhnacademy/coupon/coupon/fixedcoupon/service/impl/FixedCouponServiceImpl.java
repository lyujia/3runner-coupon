package com.nhnacademy.coupon.coupon.fixedcoupon.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.coupontype.exception.CouponTypeDoesNotExistException;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.coupon.fixedcoupon.dto.response.ReadFixedCouponResponse;
import com.nhnacademy.coupon.coupon.fixedcoupon.repository.FixedCouponRepository;
import com.nhnacademy.coupon.coupon.fixedcoupon.service.FixedCouponService;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.fixedcoupon.FixedCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 고정 쿠폰 서비스 구현체.
 *
 * @author 김병우
 */
@Transactional
@Service
@RequiredArgsConstructor
public class FixedCouponServiceImpl implements FixedCouponService {
    private final ObjectMapper objectMapper;
    private final FixedCouponRepository fixedCouponRepository;
    private final CouponTypeRepository couponTypeRepository;

    @Override
    public Long create(int discountPrice) {
        String type = "고정할인 : "+discountPrice;
        CouponType couponType = new CouponType(type);
        couponTypeRepository.save(couponType);

        FixedCoupon fixedCoupon = new FixedCoupon(couponType, discountPrice);
        fixedCouponRepository.save(fixedCoupon);

        return couponType.getId();
    }

    @Override
    public ReadFixedCouponResponse read(Long couponTypeId) {
        CouponType couponType = couponTypeRepository
                .findById(couponTypeId)
                .orElseThrow(()-> new CouponTypeDoesNotExistException(couponTypeId+"가 없습니다"));

        Optional<FixedCoupon> fixedCouponOptional = fixedCouponRepository
                .findByCouponType(couponType);

        FixedCoupon fixedCoupon = fixedCouponOptional.orElseGet(() -> new FixedCoupon(couponType, 0));

        return ReadFixedCouponResponse.builder()
                .fixedCouponId(fixedCoupon.getId())
                .couponTypeId(couponTypeId)
                .discountPrice(fixedCoupon.getDiscountPrice())
                .build();
    }
}
