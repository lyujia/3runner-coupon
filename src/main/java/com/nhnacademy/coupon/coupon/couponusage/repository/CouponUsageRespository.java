package com.nhnacademy.coupon.coupon.couponusage.repository;

import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponUsageRespository extends JpaRepository<CouponUsage, Long> {
}
