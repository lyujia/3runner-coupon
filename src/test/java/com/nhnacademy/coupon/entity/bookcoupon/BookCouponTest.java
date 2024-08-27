package com.nhnacademy.coupon.entity.bookcoupon;

import com.nhnacademy.coupon.entity.bookcouponusage.BookCouponUsage;
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
class BookCouponTest {

    @Mock
    private BookCouponUsage bookCouponUsage;

    @InjectMocks
    private BookCoupon bookCoupon;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookCouponConstructor() {
        Long bookId = 1L;
        bookCoupon = new BookCoupon();
        bookCoupon = new BookCoupon(bookId);
        assertEquals(bookId, bookCoupon.getBookId());
    }

    @Test
    void testSettersAndGetters() {
        Long bookId = 2L;
        bookCoupon.setBookId(bookId);
        assertEquals(bookId, bookCoupon.getBookId());

        List<BookCouponUsage> bookCouponUsages = new ArrayList<>();
        bookCouponUsages.add(bookCouponUsage);
        bookCoupon.setBookCouponUsages(bookCouponUsages);
        assertEquals(bookCouponUsages, bookCoupon.getBookCouponUsages());
    }


    @Test
    void testIdSetterAndGetter() {
        Long id = 1L;
        bookCoupon.setId(id);
        assertEquals(id, bookCoupon.getId());
    }
}