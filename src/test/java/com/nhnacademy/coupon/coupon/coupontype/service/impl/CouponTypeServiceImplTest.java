package com.nhnacademy.coupon.coupon.coupontype.service.impl;

import com.nhnacademy.coupon.coupon.coupontype.dto.response.ReadCouponTypeResponse;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
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
class CouponTypeServiceImplTest {

    @Mock
    private CouponTypeRepository couponTypeRepository;

    @InjectMocks
    private CouponTypeServiceImpl couponTypeService;

    @Test
    void readAll_shouldReturnListOfReadCouponTypeResponse_whenCouponTypesExist() {
        CouponType couponType1 = new CouponType("타입1");
        couponType1.setId(1L);

        CouponType couponType2 = new CouponType("타입2");
        couponType2.setId(2L);

        List<CouponType> couponTypes = Arrays.asList(couponType1, couponType2);

        when(couponTypeRepository.findAll()).thenReturn(couponTypes);

        List<ReadCouponTypeResponse> responses = couponTypeService.readAll();

        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals(1L, responses.get(0).couponTypeId());
        assertEquals("타입1", responses.get(0).type());

        assertEquals(2L, responses.get(1).couponTypeId());
        assertEquals("타입2", responses.get(1).type());

        verify(couponTypeRepository).findAll();
    }

    @Test
    void readAll_shouldReturnEmptyList_whenNoCouponTypesExist() {
        when(couponTypeRepository.findAll()).thenReturn(Arrays.asList());

        List<ReadCouponTypeResponse> responses = couponTypeService.readAll();

        assertNotNull(responses);
        assertTrue(responses.isEmpty());

        verify(couponTypeRepository).findAll();
    }

}