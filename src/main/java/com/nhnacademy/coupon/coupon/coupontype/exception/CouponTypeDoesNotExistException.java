package com.nhnacademy.coupon.coupon.coupontype.exception;

public class CouponTypeDoesNotExistException extends RuntimeException{
    public CouponTypeDoesNotExistException(String message) {
        super(message);
    }
}
