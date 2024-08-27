package com.nhnacademy.coupon.entity.categorycoupon;

import com.nhnacademy.coupon.entity.categorycouponusage.CategoryCouponUsage;
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
class CategoryCouponTest {

    @Mock
    private CategoryCouponUsage categoryCouponUsage;

    @InjectMocks
    private CategoryCoupon categoryCoupon;

    @Test
    void testCategoryCouponConstructor() {
        Long categoryId = 1L;
        categoryCoupon = new CategoryCoupon(categoryId);
        assertEquals(categoryId, categoryCoupon.getCategoryId());
    }

    @Test
    void testSettersAndGetters() {
        Long categoryId = 2L;
        categoryCoupon.setCategoryId(categoryId);
        assertEquals(categoryId, categoryCoupon.getCategoryId());

        List<CategoryCouponUsage> categoryCouponUsages = new ArrayList<>();
        categoryCouponUsages.add(categoryCouponUsage);
        categoryCoupon.setCategoryCouponUsages(categoryCouponUsages);
        assertEquals(categoryCouponUsages, categoryCoupon.getCategoryCouponUsages());
    }

    @Test
    void testIdSetterAndGetter() {
        Long id = 1L;
        categoryCoupon.setId(id);
        assertEquals(id, categoryCoupon.getId());
    }
}