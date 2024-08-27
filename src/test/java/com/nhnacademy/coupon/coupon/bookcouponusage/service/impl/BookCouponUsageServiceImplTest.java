package com.nhnacademy.coupon.coupon.bookcouponusage.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.bookcoupon.repository.BookCouponRepository;
import com.nhnacademy.coupon.coupon.bookcouponusage.feign.BookControllerClient;
import com.nhnacademy.coupon.coupon.bookcouponusage.feign.dto.BookForCouponResponse;
import com.nhnacademy.coupon.coupon.bookcouponusage.repository.BookCouponUsageRepository;
import com.nhnacademy.coupon.coupon.couponusage.repository.CouponUsageRespository;
import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.entity.bookcouponusage.BookCouponUsage;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import com.nhnacademy.coupon.global.util.ApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BookCouponUsageServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CouponUsageRespository couponUsageRespository;

    @Mock
    private BookCouponUsageRepository bookCouponUsageRepository;

    @Mock
    private BookCouponRepository bookCouponRepository;

    @Mock
    private BookControllerClient bookControllerClient;

    @InjectMocks
    private BookCouponUsageServiceImpl bookCouponUsageService;

    @Test
    void testCreateWithNewBookCoupon() {
        List<Long> bookIds = Arrays.asList(1L, 2L);
        List<BookForCouponResponse> bookResponses = Arrays.asList(
                new BookForCouponResponse(1L, "Book1"),
                new BookForCouponResponse(2L, "Book2")
        );

        when(bookControllerClient.readAllBooksForCoupon(any())).thenReturn(ApiResponse.createSuccess(bookResponses));
        when(couponUsageRespository.save(any(CouponUsage.class))).thenAnswer(invocation -> {
            CouponUsage couponUsage = invocation.getArgument(0);
            couponUsage.setId(1L);
            return couponUsage;
        });
        when(bookCouponRepository.findById(any())).thenReturn(Optional.empty());
        when(bookCouponRepository.save(any(BookCoupon.class))).thenAnswer(invocation -> {
            BookCoupon bookCoupon = invocation.getArgument(0);
            bookCoupon.setId(bookCoupon.getBookId());
            return bookCoupon;
        });
        when(bookCouponUsageRepository.save(any(BookCouponUsage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Long couponUsageId = bookCouponUsageService.create(bookIds);

        assertThat(couponUsageId).isEqualTo(1L);

        verify(bookCouponRepository, times(2)).save(any(BookCoupon.class));
    }

    @Test
    void testCreateWithExistingBookCoupon() {
        List<Long> bookIds = Arrays.asList(1L, 2L);
        List<BookForCouponResponse> bookResponses = Arrays.asList(
                new BookForCouponResponse(1L, "Book1"),
                new BookForCouponResponse(2L, "Book2")
        );
        BookCoupon existingBookCoupon1 = new BookCoupon(1L);
        existingBookCoupon1.setId(1L);
        BookCoupon existingBookCoupon2 = new BookCoupon(2L);
        existingBookCoupon2.setId(2L);

        when(bookControllerClient.readAllBooksForCoupon(any())).thenReturn(ApiResponse.createSuccess(bookResponses));
        when(couponUsageRespository.save(any(CouponUsage.class))).thenAnswer(invocation -> {
            CouponUsage couponUsage = invocation.getArgument(0);
            couponUsage.setId(1L);
            return couponUsage;
        });
        when(bookCouponRepository.findById(1L)).thenReturn(Optional.of(existingBookCoupon1));
        when(bookCouponRepository.findById(2L)).thenReturn(Optional.of(existingBookCoupon2));
        when(bookCouponUsageRepository.save(any(BookCouponUsage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Long couponUsageId = bookCouponUsageService.create(bookIds);

        assertThat(couponUsageId).isEqualTo(1L);

        verify(bookCouponRepository, times(0)).save(existingBookCoupon1);
        verify(bookCouponRepository, times(0)).save(existingBookCoupon2);
    }

    @Test
    void testReadBooks() {
        Long couponUsageId = 1L;
        List<Long> bookIds = Arrays.asList(1L, 2L);

        when(bookCouponUsageRepository.findBookIdsByCouponUsageId(couponUsageId)).thenReturn(bookIds);

        List<Long> result = bookCouponUsageService.readBooks(couponUsageId);

        assertThat(result).isEqualTo(bookIds);
    }
}