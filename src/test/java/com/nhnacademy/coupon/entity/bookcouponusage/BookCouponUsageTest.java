package com.nhnacademy.coupon.entity.bookcouponusage;

import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
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
class BookCouponUsageTest {

    @Mock
    private CouponUsage couponUsage;

    @Mock
    private BookCoupon bookCoupon;

    @InjectMocks
    private BookCouponUsage bookCouponUsage;



    @Test
    void testBookCouponUsageConstructor() {
        bookCouponUsage = new BookCouponUsage();
        bookCouponUsage = new BookCouponUsage(couponUsage, bookCoupon);
        assertEquals(couponUsage, bookCouponUsage.getCouponUsage());
        assertEquals(bookCoupon, bookCouponUsage.getBookCoupon());
    }

    @Test
    void testSettersAndGetters() {
        bookCouponUsage.setCouponUsage(couponUsage);
        bookCouponUsage.setBookCoupon(bookCoupon);

        assertEquals(couponUsage, bookCouponUsage.getCouponUsage());
        assertEquals(bookCoupon, bookCouponUsage.getBookCoupon());
    }

    @Test
    void testIdSetterAndGetter() {
        Long id = 1L;
        bookCouponUsage.setId(id);
        assertEquals(id, bookCouponUsage.getId());
    }
}