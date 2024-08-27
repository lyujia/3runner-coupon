package com.nhnacademy.coupon.coupon.fixedcoupon.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.fixedcoupon.dto.response.ReadFixedCouponResponse;
import com.nhnacademy.coupon.coupon.fixedcoupon.repository.FixedCouponRepository;
import com.nhnacademy.coupon.entity.fixedcoupon.FixedCoupon;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.nhnacademy.coupon.coupon.coupontype.exception.CouponTypeDoesNotExistException;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FixedCouponServiceImplTest {
    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private FixedCouponRepository fixedCouponRepository;

    @Mock
    private CouponTypeRepository couponTypeRepository;

    @InjectMocks
    private FixedCouponServiceImpl fixedCouponService;

    private CouponType couponType;
    private FixedCoupon fixedCoupon;

    @BeforeEach
    public void setUp() {
        couponType = new CouponType("고정할인 : 1000");
        fixedCoupon = new FixedCoupon(couponType, 1000);
    }

    @Test
    void create_shouldReturnCouponTypeId_whenFixedCouponCreated() {
        int discountPrice = 1000;
        String type = "고정할인 : " + discountPrice;
        CouponType newCouponType = new CouponType(type);
        newCouponType.setId(1L);

        when(couponTypeRepository.save(any(CouponType.class))).thenReturn(newCouponType);
        when(fixedCouponRepository.save(any(FixedCoupon.class))).thenReturn(fixedCoupon);

        assertDoesNotThrow(()->{
            fixedCouponService.create(discountPrice);
        });

        verify(couponTypeRepository).save(any(CouponType.class));
        verify(fixedCouponRepository).save(any(FixedCoupon.class));
    }


    @Test
    void read_shouldReturnFixedCouponResponse_whenCouponTypeExists() {
        Long couponTypeId = 1L;
        couponType.setId(couponTypeId);
        fixedCoupon.setId(1L);

        when(couponTypeRepository.findById(couponTypeId)).thenReturn(Optional.of(couponType));
        when(fixedCouponRepository.findByCouponType(couponType)).thenReturn(Optional.of(fixedCoupon));

        ReadFixedCouponResponse response = fixedCouponService.read(couponTypeId);

        assertNotNull(response);
        assertEquals(1L, response.fixedCouponId());
        assertEquals(couponTypeId, response.couponTypeId());
        assertEquals(1000, response.discountPrice());

        verify(couponTypeRepository).findById(couponTypeId);
        verify(fixedCouponRepository).findByCouponType(couponType);
    }

    @Test
    void read_shouldReturnNewFixedCouponResponse_whenCouponTypeExistsButNoFixedCoupon() {
        Long couponTypeId = 1L;
        couponType.setId(couponTypeId);

        when(couponTypeRepository.findById(couponTypeId)).thenReturn(Optional.of(couponType));
        when(fixedCouponRepository.findByCouponType(couponType)).thenReturn(Optional.empty());

        ReadFixedCouponResponse response = fixedCouponService.read(couponTypeId);

        assertNotNull(response);
        assertNull(response.fixedCouponId());
        assertEquals(couponTypeId, response.couponTypeId());
        assertEquals(0, response.discountPrice());

        verify(couponTypeRepository).findById(couponTypeId);
        verify(fixedCouponRepository).findByCouponType(couponType);
    }

    @Test
    void read_shouldThrowException_whenCouponTypeDoesNotExist() {
        Long couponTypeId = 1L;

        when(couponTypeRepository.findById(couponTypeId)).thenReturn(Optional.empty());

        assertThrows(CouponTypeDoesNotExistException.class, () -> {
            fixedCouponService.read(couponTypeId);
        });

        verify(couponTypeRepository).findById(couponTypeId);
        verify(fixedCouponRepository, never()).findByCouponType(any());
    }

}