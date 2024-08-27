package com.nhnacademy.coupon.coupon.ratiocoupon.repository;

import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.ratiocoupon.RatioCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/**
 * 비율 쿠폰 JPA 저장소 인터페이스.
 *
 * @author 김병우
 */
public interface RatioCouponRepository extends JpaRepository<RatioCoupon, Long> {
    Optional<RatioCoupon> findByCouponType(CouponType couponType);
}
