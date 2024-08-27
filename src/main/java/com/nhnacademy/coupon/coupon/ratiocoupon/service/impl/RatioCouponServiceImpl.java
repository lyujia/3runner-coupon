package com.nhnacademy.coupon.coupon.ratiocoupon.service.impl;

import com.nhnacademy.coupon.coupon.coupontype.exception.CouponTypeDoesNotExistException;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.coupon.ratiocoupon.dto.response.ReadRatioCouponResponse;
import com.nhnacademy.coupon.coupon.ratiocoupon.repository.RatioCouponRepository;
import com.nhnacademy.coupon.coupon.ratiocoupon.service.RatioCouponService;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.ratiocoupon.RatioCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 비율 쿠폰 서비스 구현체
 *
 * @author 김병우
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RatioCouponServiceImpl implements RatioCouponService {
    private final RatioCouponRepository ratioCouponRepository;
    private final CouponTypeRepository couponTypeRepository;

    @Override
    public Long create(double discountRate, int discountMaxPrice) {
        String type = "할인율 : " + discountRate + ", 최대할인가 : " + discountMaxPrice;

        CouponType couponType = new CouponType(type);
        couponTypeRepository.save(couponType);

        RatioCoupon ratioCoupon = new RatioCoupon(couponType, discountRate, discountMaxPrice);
        ratioCouponRepository.save(ratioCoupon);

        return couponType.getId();
    }

    @Override
    public ReadRatioCouponResponse read(Long couponTypeId) {
        CouponType couponType = couponTypeRepository
                .findById(couponTypeId)
                .orElseThrow(()-> new CouponTypeDoesNotExistException(couponTypeId+"가 없습니다"));

        Optional<RatioCoupon> ratioCouponOptional = ratioCouponRepository
                .findByCouponType(couponType);

        RatioCoupon ratioCoupon = ratioCouponOptional
                .orElseGet(() -> new RatioCoupon(couponType, 0, 0));

        return ReadRatioCouponResponse.builder()
                .ratioCouponId(ratioCoupon.getId())
                .couponTypeId(couponTypeId)
                .discountRate(ratioCoupon.getDiscountRate())
                .discountMaxPrice(ratioCoupon.getDiscountMaxPrice())
                .build();
    }
}
