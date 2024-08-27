package com.nhnacademy.coupon.entity.couponform;

import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CouponFormTest {

    @Mock
    private CouponType couponType;

    @Mock
    private CouponUsage couponUsage;

    @InjectMocks
    private CouponForm couponForm;

    @Test
    void testCouponFormConstructor() {
        ZonedDateTime startDate = ZonedDateTime.now();
        ZonedDateTime endDate = ZonedDateTime.now().plusDays(7);
        ZonedDateTime createdAt = ZonedDateTime.now();
        String name = "Test Coupon";
        UUID code = UUID.randomUUID();
        Integer maxPrice = 1000;
        Integer minPrice = 100;

        couponForm = new CouponForm(1L, startDate, endDate, createdAt, name, code, maxPrice, minPrice, couponType, couponUsage);
        assertEquals(startDate, couponForm.getStartDate());
        assertEquals(endDate, couponForm.getEndDate());
        assertEquals(createdAt, couponForm.getCreatedAt());
        assertEquals(name, couponForm.getName());
        assertEquals(code, couponForm.getCode());
        assertEquals(maxPrice, couponForm.getMaxPrice());
        assertEquals(minPrice, couponForm.getMinPrice());
        assertEquals(couponType, couponForm.getCouponType());
        assertEquals(couponUsage, couponForm.getCouponUsage());
    }

    @Test
    void testSetBasicDetails() {
        ZonedDateTime startDate = ZonedDateTime.now();
        ZonedDateTime endDate = ZonedDateTime.now().plusDays(7);
        String name = "Test Coupon";
        UUID code = UUID.randomUUID();
        Integer maxPrice = 1000;
        Integer minPrice = 100;

        couponForm.setBasicDetails(startDate, endDate, name, code, maxPrice, minPrice);

        assertEquals(startDate, couponForm.getStartDate());
        assertEquals(endDate, couponForm.getEndDate());
        assertEquals(name, couponForm.getName());
        assertEquals(code, couponForm.getCode());
        assertEquals(maxPrice, couponForm.getMaxPrice());
        assertEquals(minPrice, couponForm.getMinPrice());
    }

    @Test
    void testSetCouponDetails() {
        couponForm.setCouponDetails(couponType, couponUsage);

        assertEquals(couponType, couponForm.getCouponType());
        assertEquals(couponUsage, couponForm.getCouponUsage());
    }

    @Test
    void testOnCreate() {
        couponForm.onCreate();

        assertEquals(ZonedDateTime.now().getDayOfYear(), couponForm.getCreatedAt().getDayOfYear());
    }

    @Test
    void testIdSetterAndGetter() {
        Long id = 1L;
        couponForm.setId(id);
        assertEquals(id, couponForm.getId());
    }
}