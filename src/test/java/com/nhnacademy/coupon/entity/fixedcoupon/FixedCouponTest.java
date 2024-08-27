package com.nhnacademy.coupon.entity.fixedcoupon;

import com.nhnacademy.coupon.entity.coupontype.CouponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FixedCouponTest {

    @Mock
    private CouponType couponType;

    @InjectMocks
    private FixedCoupon fixedCoupon;

    @Test
    void testFixedCouponConstructor() {
        int discountPrice = 1000;
        fixedCoupon = new FixedCoupon(couponType, discountPrice);
        assertEquals(couponType, fixedCoupon.getCouponType());
        assertEquals(discountPrice, fixedCoupon.getDiscountPrice());
    }

    @Test
    void testSettersAndGetters() {
        CouponType newCouponType = new CouponType();
        int newDiscountPrice = 1500;

        fixedCoupon.setCouponType(newCouponType);
        fixedCoupon.setDiscountPrice(newDiscountPrice);

        assertEquals(newCouponType, fixedCoupon.getCouponType());
        assertEquals(newDiscountPrice, fixedCoupon.getDiscountPrice());
    }

    @Test
    void testIdSetterAndGetter() {
        Long id = 1L;
        fixedCoupon.setId(id);
        assertEquals(id, fixedCoupon.getId());
    }
}