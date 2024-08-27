package com.nhnacademy.coupon.coupon.couponform.exception;

/**
 * 쿠폰 폼이 존재하지 않을 경우의 예외처리.
 *
 * @author 김병우
 */
public class CouponFormNotExistException extends RuntimeException{
    public CouponFormNotExistException(String message) {
        super(message);
    }
}
