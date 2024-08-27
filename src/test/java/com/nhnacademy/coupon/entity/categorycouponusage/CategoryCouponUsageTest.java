package com.nhnacademy.coupon.entity.categorycouponusage;

import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CategoryCouponUsageTest {

    @Mock
    private CouponUsage couponUsage;

    @Mock
    private CategoryCoupon categoryCoupon;

    @InjectMocks
    private CategoryCouponUsage categoryCouponUsage;


    @Test
    void testCategoryCouponUsageConstructor() {
        categoryCouponUsage = new CategoryCouponUsage(couponUsage, categoryCoupon);
        assertEquals(couponUsage, categoryCouponUsage.getCouponUsage());
        assertEquals(categoryCoupon, categoryCouponUsage.getCategoryCoupon());
    }

    @Test
    void testSettersAndGetters() {
        categoryCouponUsage.setCouponUsage(couponUsage);
        categoryCouponUsage.setCategoryCoupon(categoryCoupon);

        assertEquals(couponUsage, categoryCouponUsage.getCouponUsage());
        assertEquals(categoryCoupon, categoryCouponUsage.getCategoryCoupon());
    }

    @Test
    void testIdSetterAndGetter() {
        Long id = 1L;
        categoryCouponUsage.setId(id);
        assertEquals(id, categoryCouponUsage.getId());
    }
}