package com.nhnacademy.coupon.coupon.couponusage.service;

import com.nhnacademy.coupon.coupon.couponusage.dto.response.ReadCouponUsageResponse;

import java.util.List;

public interface CouponUsageService {
    List<ReadCouponUsageResponse> readAll();
}
