package com.nhnacademy.coupon.coupon.couponform.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.nhnacademy.coupon.coupon.coupontype.exception.CouponTypeDoesNotExistException;
import com.nhnacademy.coupon.coupon.couponusage.exception.CouponUsageDoesNotExistException;
import com.nhnacademy.coupon.coupon.fixedcoupon.dto.response.ReadFixedCouponResponse;
import com.nhnacademy.coupon.coupon.ratiocoupon.dto.response.ReadRatioCouponResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.bookcouponusage.service.BookCouponUsageService;
import com.nhnacademy.coupon.coupon.categorycouponusage.service.CategoryCouponUsageService;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.repository.CouponFormRepository;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.coupon.couponusage.repository.CouponUsageRespository;
import com.nhnacademy.coupon.coupon.fixedcoupon.service.FixedCouponService;
import com.nhnacademy.coupon.coupon.ratiocoupon.service.RatioCouponService;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@ExtendWith(MockitoExtension.class)
class CouponFormServiceImplTest {

    @Mock
    private CouponUsageRespository couponUsageRespository;

    @Mock
    private CouponTypeRepository couponTypeRepository;

    @Mock
    private CouponFormRepository couponFormRepository;

    @Mock
    private BookCouponUsageService bookCouponUsageService;

    @Mock
    private CategoryCouponUsageService categoryCouponUsageService;

    @Mock
    private FixedCouponService fixedCouponService;

    @Mock
    private RatioCouponService ratioCouponService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private CouponFormServiceImpl couponFormService;


    @Test
    void create_shouldReturnCouponFormId_whenCouponFormCreated() {
        Long couponUsageId = 1L;
        Long couponTypeId = 1L;
        CreateCouponFormRequest request = CreateCouponFormRequest.builder()
                .startDate(ZonedDateTime.now().minusDays(1))
                .endDate(ZonedDateTime.now().plusDays(1))
                .name("Test Coupon")
                .maxPrice(10000)
                .minPrice(5000)
                .couponTypeId(1L)
                .couponUsageId(1L)
                .build();
        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setId(couponUsageId);

        CouponType couponType = new CouponType();
        couponType.setId(couponTypeId);

        CouponForm couponForm = new CouponForm();
        couponForm.setBasicDetails(
                request.startDate(),
                request.endDate(),
                request.name(),
                UUID.randomUUID(),
                request.maxPrice(),
                request.minPrice()
        );
        couponForm.setCouponDetails(
                couponType,
                couponUsage
        );

        when(couponUsageRespository.findById(couponUsageId)).thenReturn(Optional.of(couponUsage));
        when(couponTypeRepository.findById(couponTypeId)).thenReturn(Optional.of(couponType));
        when(couponFormRepository.save(any(CouponForm.class))).thenReturn(couponForm);

        assertDoesNotThrow(()->{
            couponFormService.create(request);
        });


        verify(couponUsageRespository).findById(couponUsageId);
        verify(couponTypeRepository).findById(couponTypeId);
        verify(couponFormRepository).save(any(CouponForm.class));
    }
    @Test
    void create_shouldThrowCouponUsageDoesNotExistException_whenCouponUsageNotFound() {
        // Given
        Long couponUsageId = 1L;
        Long couponTypeId = 1L;

        CreateCouponFormRequest request = CreateCouponFormRequest.builder()
                .startDate(ZonedDateTime.now().minusDays(1))
                .endDate(ZonedDateTime.now().plusDays(1))
                .name("Test Coupon")
                .maxPrice(10000)
                .minPrice(5000)
                .couponTypeId(couponTypeId)
                .couponUsageId(couponUsageId)
                .build();

        when(couponUsageRespository.findById(couponUsageId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CouponUsageDoesNotExistException.class, () -> {
            couponFormService.create(request);
        });

        verify(couponUsageRespository).findById(couponUsageId);
        verify(couponTypeRepository, never()).findById(anyLong());
        verify(couponFormRepository, never()).save(any(CouponForm.class));
    }

    @Test
    void create_shouldThrowCouponTypeDoesNotExistException_whenCouponTypeNotFound() {
        // Given
        Long couponUsageId = 1L;
        Long couponTypeId = 1L;

        CreateCouponFormRequest request = CreateCouponFormRequest.builder()
                .startDate(ZonedDateTime.now().minusDays(1))
                .endDate(ZonedDateTime.now().plusDays(1))
                .name("Test Coupon")
                .maxPrice(10000)
                .minPrice(5000)
                .couponTypeId(couponTypeId)
                .couponUsageId(couponUsageId)
                .build();

        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setId(couponUsageId);

        when(couponUsageRespository.findById(couponUsageId)).thenReturn(Optional.of(couponUsage));
        when(couponTypeRepository.findById(couponTypeId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CouponTypeDoesNotExistException.class, () -> {
            couponFormService.create(request);
        });

        verify(couponUsageRespository).findById(couponUsageId);
        verify(couponTypeRepository).findById(couponTypeId);
        verify(couponFormRepository, never()).save(any(CouponForm.class));
    }

    @Test
    void read_shouldReturnCouponForm_whenCouponFormExists() {
        // Given
        Long couponFormId = 1L;
        CouponForm couponForm = new CouponForm();
        couponForm.setId(couponFormId);

        when(couponFormRepository.findById(couponFormId)).thenReturn(Optional.of(couponForm));

        // When
        CouponForm result = couponFormService.read(couponFormId);

        // Then
        assertNotNull(result);
        assertEquals(couponFormId, result.getId());
        verify(couponFormRepository).findById(couponFormId);
    }

    @Test
    void read_shouldThrowException_whenCouponFormDoesNotExist() {
        // Given
        Long couponFormId = 1L;

        when(couponFormRepository.findById(couponFormId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CouponFormNotExistException.class, () -> {
            couponFormService.read(couponFormId);
        });

        verify(couponFormRepository).findById(couponFormId);
    }

    @Test
    void readAll_shouldReturnListOfReadCouponFormResponse_whenCouponFormsExist() {
        // Given
        Long couponFormId1 = 1L;
        Long couponFormId2 = 2L;
        Long couponTypeId = 1L;
        Long couponUsageId = 1L;

        CouponType couponType = new CouponType();
        couponType.setId(couponTypeId);

        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setId(couponUsageId);

        CouponForm couponForm1 = new CouponForm();
        couponForm1.setId(couponFormId1);
        couponForm1.setCouponType(couponType);
        couponForm1.setCouponUsage(couponUsage);
        couponForm1.setStartDate(ZonedDateTime.now().minusDays(1));
        couponForm1.setEndDate(ZonedDateTime.now().plusDays(1));
        couponForm1.setCreatedAt(ZonedDateTime.now().minusDays(2));
        couponForm1.setName("Coupon 1");
        couponForm1.setCode(UUID.randomUUID());
        couponForm1.setMaxPrice(10000);
        couponForm1.setMinPrice(5000);

        CouponForm couponForm2 = new CouponForm();
        couponForm2.setId(couponFormId2);
        couponForm2.setCouponType(couponType);
        couponForm2.setCouponUsage(couponUsage);
        couponForm2.setStartDate(ZonedDateTime.now().minusDays(1));
        couponForm2.setEndDate(ZonedDateTime.now().plusDays(1));
        couponForm2.setCreatedAt(ZonedDateTime.now().minusDays(2));
        couponForm2.setName("Coupon 2");
        couponForm2.setCode(UUID.randomUUID());
        couponForm2.setMaxPrice(20000);
        couponForm2.setMinPrice(10000);

        List<CouponForm> couponForms = Arrays.asList(couponForm1, couponForm2);

        when(couponFormRepository.findAll()).thenReturn(couponForms);

        when(bookCouponUsageService.readBooks(couponUsageId)).thenReturn(Arrays.asList(1L, 2L));
        when(categoryCouponUsageService.readCategorys(couponUsageId)).thenReturn(Arrays.asList(1L, 2L));
        when(fixedCouponService.read(couponTypeId)).thenReturn(new ReadFixedCouponResponse(1L, couponTypeId, 1000));
        when(ratioCouponService.read(couponTypeId)).thenReturn(new ReadRatioCouponResponse(1L, couponTypeId, 10, 5000));

        // When
        Assertions.assertDoesNotThrow(()->{
            List<ReadCouponFormResponse> responses = couponFormService.readAllForms();
        });

    }

    @Test
    void readAllForms_shouldReturnListOfReadCouponFormResponse_whenCouponFormsExist() {
        Long couponFormId1 = 1L;
        Long couponFormId2 = 2L;
        Long couponTypeId = 1L;
        Long couponUsageId = 1L;

        CouponType couponType = new CouponType();
        couponType.setId(couponTypeId);

        CouponUsage couponUsage = new CouponUsage();
        couponUsage.setId(couponUsageId);

        CouponForm couponForm1 = new CouponForm();
        couponForm1.setId(couponFormId1);
        couponForm1.setCouponType(couponType);
        couponForm1.setCouponUsage(couponUsage);
        couponForm1.setStartDate(ZonedDateTime.now().minusDays(1));
        couponForm1.setEndDate(ZonedDateTime.now().plusDays(1));
        couponForm1.setCreatedAt(ZonedDateTime.now().minusDays(2));
        couponForm1.setName("Coupon 1");
        couponForm1.setCode(UUID.randomUUID());
        couponForm1.setMaxPrice(10000);
        couponForm1.setMinPrice(5000);

        CouponForm couponForm2 = new CouponForm();
        couponForm2.setId(couponFormId2);
        couponForm2.setCouponType(couponType);
        couponForm2.setCouponUsage(couponUsage);
        couponForm2.setStartDate(ZonedDateTime.now().minusDays(1));
        couponForm2.setEndDate(ZonedDateTime.now().plusDays(1));
        couponForm2.setCreatedAt(ZonedDateTime.now().minusDays(2));
        couponForm2.setName("Coupon 2");
        couponForm2.setCode(UUID.randomUUID());
        couponForm2.setMaxPrice(20000);
        couponForm2.setMinPrice(10000);

        List<Long> couponFormIds = Arrays.asList(couponFormId1, couponFormId2);
        List<CouponForm> couponForms = Arrays.asList(couponForm1, couponForm2);

        when(couponFormRepository.findAllByIdIn(couponFormIds)).thenReturn(couponForms);

        // Mocking dependent services
        when(bookCouponUsageService.readBooks(couponUsageId)).thenReturn(Arrays.asList(1L, 2L));
        when(categoryCouponUsageService.readCategorys(couponUsageId)).thenReturn(Arrays.asList(1L, 2L));
        when(fixedCouponService.read(couponTypeId)).thenReturn(new ReadFixedCouponResponse(1L, couponTypeId, 1000));
        when(ratioCouponService.read(couponTypeId)).thenReturn(new ReadRatioCouponResponse(1L, couponTypeId, 10, 5000));

        // When
        Assertions.assertDoesNotThrow(()->{
            List<ReadCouponFormResponse> responses = couponFormService.readAll(couponFormIds);
        });
    }

    @Test
    void sendNoticeCouponsExpiringThreeDaysLater_shouldSendNotification() throws Exception {
        // Given
        CouponForm couponForm = new CouponForm();
        List<CouponForm> coupons = List.of(couponForm);

        when(couponFormRepository.findCouponsExpiringThreeDaysLater()).thenReturn(coupons);
        when(objectMapper.writeValueAsString(any())).thenReturn("data");

        // When
        couponFormService.sendNoticeCouponsExpiringThreeDaysLater();

        // Then
        verify(couponFormRepository).findCouponsExpiringThreeDaysLater();
        verify(rabbitTemplate).convertAndSend(anyString(), anyString());
    }
}