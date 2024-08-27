package com.nhnacademy.coupon.entity.couponusage;

import com.nhnacademy.coupon.entity.bookcouponusage.BookCouponUsage;
import com.nhnacademy.coupon.entity.categorycouponusage.CategoryCouponUsage;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CouponUsageTest {

    @Mock
    private CouponForm couponForm;

    @Mock
    private BookCouponUsage bookCouponUsage;

    @Mock
    private CategoryCouponUsage categoryCouponUsage;

    @InjectMocks
    private CouponUsage couponUsage;


    @Test
    void testCouponUsageConstructor() {
        String usage = "Discount";
        couponUsage = new CouponUsage(usage);
        assertEquals(usage, couponUsage.getUsage());
    }

    @Test
    void testSettersAndGetters() {
        String usage = "Special Offer";
        couponUsage.setUsage(usage);
        assertEquals(usage, couponUsage.getUsage());

        List<CouponForm> couponForms = new ArrayList<>();
        couponForms.add(couponForm);
        couponUsage.setCouponForms(couponForms);
        assertEquals(couponForms, couponUsage.getCouponForms());

        List<BookCouponUsage> bookCouponUsages = new ArrayList<>();
        bookCouponUsages.add(bookCouponUsage);
        couponUsage.setBookCouponUsages(bookCouponUsages);
        assertEquals(bookCouponUsages, couponUsage.getBookCouponUsages());

        List<CategoryCouponUsage> categoryCouponUsages = new ArrayList<>();
        categoryCouponUsages.add(categoryCouponUsage);
        couponUsage.setCategoryCouponUsages(categoryCouponUsages);
        assertEquals(categoryCouponUsages, couponUsage.getCategoryCouponUsages());
    }

    @Test
    void testIdSetterAndGetter() {
        Long id = 1L;
        couponUsage.setId(id);
        assertEquals(id, couponUsage.getId());
    }
}