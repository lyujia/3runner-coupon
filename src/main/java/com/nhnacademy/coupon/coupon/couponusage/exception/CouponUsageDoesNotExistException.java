package com.nhnacademy.coupon.coupon.couponusage.exception;

public class CouponUsageDoesNotExistException extends RuntimeException{
    public CouponUsageDoesNotExistException(String message) {
        super(message);
    }
}
