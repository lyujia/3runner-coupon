package com.nhnacademy.coupon.coupon.ratiocoupon.service.impl;

import com.nhnacademy.coupon.coupon.coupontype.exception.CouponTypeDoesNotExistException;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.coupon.ratiocoupon.dto.response.ReadRatioCouponResponse;
import com.nhnacademy.coupon.coupon.ratiocoupon.repository.RatioCouponRepository;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.ratiocoupon.RatioCoupon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RatioCouponServiceImplTest {

    @Mock
    private RatioCouponRepository ratioCouponRepository;

    @Mock
    private CouponTypeRepository couponTypeRepository;

    @InjectMocks
    private RatioCouponServiceImpl ratioCouponService ;

    private double discountRate;
    private int discountMaxPrice;
    private CouponType couponType;
    private RatioCoupon ratioCoupon;

    @BeforeEach
    void setUp() {
        discountRate = 0.1;
        discountMaxPrice = 1000;
        couponType = new CouponType("할인율 : " + discountRate + ", 최대할인가 : " + discountMaxPrice);
        couponType.setId(1L);
        ratioCoupon = new RatioCoupon(couponType, discountRate, discountMaxPrice);
        ratioCoupon.setId(1L);
    }

    @Test
    void create_shouldCreateAndSaveCouponTypeAndRatioCoupon() {
        Assertions.assertDoesNotThrow(()->{
            ratioCouponService.create(discountRate, discountMaxPrice);
        });
    }

    @Test
    void read_shouldReturnRatioCouponResponse_whenCouponTypeExists() {
        CouponType couponType1 = new CouponType("123123");
        couponType1.setId(1L);

        doReturn(Optional.of(couponType1)).when(couponTypeRepository).findById(anyLong());

        when(ratioCouponRepository.findByCouponType(couponType1)).thenReturn(Optional.of(ratioCoupon));

        Assertions.assertDoesNotThrow(()->{
            ratioCouponService.read(anyLong());
        });

    }

    @Test
    void read_shouldThrowException_whenCouponTypeDoesNotExist() {
        Long couponTypeId = 1L;
        CouponTypeDoesNotExistException exception = assertThrows(
                CouponTypeDoesNotExistException.class,
                () -> ratioCouponService.read(couponTypeId)
        );
        assertEquals(couponTypeId + "가 없습니다", exception.getMessage());
    }

    @Test
    void read_shouldReturnDefaultRatioCouponResponse_whenRatioCouponDoesNotExist() {
        Long couponTypeId = 5L;
        couponType.setId(couponTypeId);
        when(couponTypeRepository.findById(couponTypeId)).thenReturn(Optional.of(couponType));
        when(ratioCouponRepository.findByCouponType(couponType)).thenReturn(Optional.empty());

        ReadRatioCouponResponse response = ratioCouponService.read(couponTypeId);

        assertNull(response.ratioCouponId());
        assertEquals(couponTypeId, response.couponTypeId());
        assertEquals(0, response.discountRate());
        assertEquals(0, response.discountMaxPrice());
    }

}