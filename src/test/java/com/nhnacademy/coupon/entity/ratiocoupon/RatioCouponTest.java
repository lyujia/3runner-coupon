package com.nhnacademy.coupon.entity.ratiocoupon;

import com.nhnacademy.coupon.entity.coupontype.CouponType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RatioCouponTest {

    @Mock
    private CouponType couponType;

    @InjectMocks
    private RatioCoupon ratioCoupon;


    @Test
    void testRatioCouponConstructor() {
        double discountRate = 0.1;
        int discountMaxPrice = 5000;
        ratioCoupon = new RatioCoupon(couponType, discountRate, discountMaxPrice);
        assertEquals(couponType, ratioCoupon.getCouponType());
        assertEquals(discountRate, ratioCoupon.getDiscountRate());
        assertEquals(discountMaxPrice, ratioCoupon.getDiscountMaxPrice());
    }

    @Test
    void testSettersAndGetters() {
        CouponType newCouponType = new CouponType();
        double newDiscountRate = 0.15;
        int newDiscountMaxPrice = 7000;

        ratioCoupon.setCouponType(newCouponType);
        ratioCoupon.setDiscountRate(newDiscountRate);
        ratioCoupon.setDiscountMaxPrice(newDiscountMaxPrice);

        assertEquals(newCouponType, ratioCoupon.getCouponType());
        assertEquals(newDiscountRate, ratioCoupon.getDiscountRate());
        assertEquals(newDiscountMaxPrice, ratioCoupon.getDiscountMaxPrice());
    }

    @Test
    void testIdSetterAndGetter() {
        Long id = 1L;
        ratioCoupon.setId(id);
        assertEquals(id, ratioCoupon.getId());
    }
}