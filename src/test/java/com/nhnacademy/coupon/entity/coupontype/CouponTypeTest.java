package com.nhnacademy.coupon.entity.coupontype;

import com.nhnacademy.coupon.entity.couponform.CouponForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class CouponTypeTest {

    @Mock
    private CouponForm couponForm;

    @InjectMocks
    private CouponType couponType;

    @Test
    void testCouponTypeConstructor() {
        String type = "Discount";
        couponType = new CouponType(type);
        assertEquals(type, couponType.getType());
    }

    @Test
    void testSettersAndGetters() {
        String type = "Special Offer";
        couponType.setType(type);
        assertEquals(type, couponType.getType());

        List<CouponForm> couponForms = new ArrayList<>();
        couponForms.add(couponForm);
        couponType.setCouponForms(couponForms);
        assertEquals(couponForms, couponType.getCouponForms());
    }

    @Test
    void testIdSetterAndGetter() {
        Long id = 1L;
        couponType.setId(id);
        assertEquals(id, couponType.getId());
    }
}