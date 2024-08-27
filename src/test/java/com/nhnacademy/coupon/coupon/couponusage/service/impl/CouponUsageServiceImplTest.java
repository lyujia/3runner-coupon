package com.nhnacademy.coupon.coupon.couponusage.service.impl;

import com.nhnacademy.coupon.coupon.couponusage.dto.response.ReadCouponUsageResponse;
import com.nhnacademy.coupon.coupon.couponusage.repository.CouponUsageRespository;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponUsageServiceImplTest {

    @Mock
    private CouponUsageRespository couponUsageRespository;

    @InjectMocks
    private CouponUsageServiceImpl couponUsageService;

    @Test
    void readAll_shouldReturnListOfReadCouponUsageResponse_whenCouponUsagesExist() {
        CouponUsage couponUsage1 = new CouponUsage();
        couponUsage1.setId(1L);
        couponUsage1.setUsage("사용처1");

        CouponUsage couponUsage2 = new CouponUsage();
        couponUsage2.setId(2L);
        couponUsage2.setUsage("사용처2");

        List<CouponUsage> couponUsages = Arrays.asList(couponUsage1, couponUsage2);

        when(couponUsageRespository.findAll()).thenReturn(couponUsages);

        List<ReadCouponUsageResponse> responses = couponUsageService.readAll();

        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals(1L, responses.get(0).couponUsageId());
        assertEquals("사용처1", responses.get(0).usage());

        assertEquals(2L, responses.get(1).couponUsageId());
        assertEquals("사용처2", responses.get(1).usage());

        verify(couponUsageRespository).findAll();
    }

    @Test
    void readAll_shouldReturnEmptyList_whenNoCouponUsagesExist() {
        when(couponUsageRespository.findAll()).thenReturn(List.of());

        List<ReadCouponUsageResponse> responses = couponUsageService.readAll();

        assertNotNull(responses);
        assertTrue(responses.isEmpty());

        verify(couponUsageRespository).findAll();
    }

}