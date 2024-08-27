package com.nhnacademy.coupon.coupon.categorycouponusage.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.nhnacademy.coupon.coupon.categorycoupon.repository.CategoryCouponRepository;
import com.nhnacademy.coupon.coupon.categorycouponusage.feign.CategoryControllerClient;
import com.nhnacademy.coupon.coupon.categorycouponusage.feign.dto.CategoryForCouponResponse;
import com.nhnacademy.coupon.coupon.categorycouponusage.repository.CategoryCouponUsageRepository;
import com.nhnacademy.coupon.coupon.couponusage.repository.CouponUsageRespository;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.categorycouponusage.CategoryCouponUsage;
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
class CategoryCouponUsageServiceImplTest {

    @Mock
    private CouponUsageRespository couponUsageRespository;

    @Mock
    private CategoryCouponUsageRepository categoryCouponUsageRepository;

    @Mock
    private CategoryCouponRepository categoryCouponRepository;

    @Mock
    private CategoryControllerClient categoryControllerClient;

    @InjectMocks
    private CategoryCouponUsageServiceImpl categoryCouponUsageService;

    @Test
    void testCreate() {
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        List<CategoryForCouponResponse> categoryResponses = Arrays.asList(
                new CategoryForCouponResponse(1L, "Category1"),
                new CategoryForCouponResponse(2L, "Category2")
        );

        when(categoryControllerClient.readAllCategoriesList(any())).thenReturn(ApiResponse.createSuccess(categoryResponses));
        when(couponUsageRespository.save(any(CouponUsage.class))).thenAnswer(invocation -> {
            CouponUsage couponUsage = invocation.getArgument(0);
            couponUsage.setId(1L); // Set the ID manually since it's usually done by the database
            return couponUsage;
        });
        when(categoryCouponRepository.findById(any())).thenReturn(Optional.empty());
        when(categoryCouponRepository.save(any(CategoryCoupon.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(categoryCouponUsageRepository.save(any(CategoryCouponUsage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Long couponUsageId = categoryCouponUsageService.create(categoryIds);

        assertThat(couponUsageId).isEqualTo(1L);
    }
    @Test
    void testCreateWithNewCategoryCoupon() {
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        List<CategoryForCouponResponse> categoryResponses = Arrays.asList(
                new CategoryForCouponResponse(1L, "Category1"),
                new CategoryForCouponResponse(2L, "Category2")
        );

        when(categoryControllerClient.readAllCategoriesList(any())).thenReturn(ApiResponse.createSuccess(categoryResponses));
        when(couponUsageRespository.save(any(CouponUsage.class))).thenAnswer(invocation -> {
            CouponUsage couponUsage = invocation.getArgument(0);
            couponUsage.setId(1L); // Set the ID manually since it's usually done by the database
            return couponUsage;
        });
        when(categoryCouponRepository.findById(any())).thenReturn(Optional.empty());
        when(categoryCouponRepository.save(any(CategoryCoupon.class))).thenAnswer(invocation -> {
            CategoryCoupon categoryCoupon = invocation.getArgument(0);
            categoryCoupon.setId(categoryCoupon.getCategoryId());
            return categoryCoupon;
        });
        when(categoryCouponUsageRepository.save(any(CategoryCouponUsage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Long couponUsageId = categoryCouponUsageService.create(categoryIds);

        assertThat(couponUsageId).isEqualTo(1L);

        // Verify that new CategoryCoupon objects are created and saved
        verify(categoryCouponRepository, times(2)).save(any(CategoryCoupon.class));
    }

    @Test
    void testCreateWithExistingCategoryCoupon() {
        List<Long> categoryIds = Arrays.asList(1L, 2L);
        List<CategoryForCouponResponse> categoryResponses = Arrays.asList(
                new CategoryForCouponResponse(1L, "Category1"),
                new CategoryForCouponResponse(2L, "Category2")
        );
        CategoryCoupon existingCategoryCoupon1 = new CategoryCoupon(1L);
        existingCategoryCoupon1.setId(1L);
        CategoryCoupon existingCategoryCoupon2 = new CategoryCoupon(2L);
        existingCategoryCoupon2.setId(2L);

        when(categoryControllerClient.readAllCategoriesList(any())).thenReturn(ApiResponse.createSuccess(categoryResponses));
        when(couponUsageRespository.save(any(CouponUsage.class))).thenAnswer(invocation -> {
            CouponUsage couponUsage = invocation.getArgument(0);
            couponUsage.setId(1L); // Set the ID manually since it's usually done by the database
            return couponUsage;
        });
        when(categoryCouponRepository.findById(1L)).thenReturn(Optional.of(existingCategoryCoupon1));
        when(categoryCouponRepository.findById(2L)).thenReturn(Optional.of(existingCategoryCoupon2));
        when(categoryCouponUsageRepository.save(any(CategoryCouponUsage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Long couponUsageId = categoryCouponUsageService.create(categoryIds);

        assertThat(couponUsageId).isEqualTo(1L);

        // Verify that existing CategoryCoupon objects are not created again
        verify(categoryCouponRepository, times(0)).save(existingCategoryCoupon1);
        verify(categoryCouponRepository, times(0)).save(existingCategoryCoupon2);
    }

    @Test
    void testReadCategorys() {
        Long couponUsageId = 1L;
        List<Long> categoryIds = Arrays.asList(1L, 2L);

        when(categoryCouponUsageRepository.findCategoryIdsByCouponUsageId(couponUsageId)).thenReturn(categoryIds);

        List<Long> result = categoryCouponUsageService.readCategorys(couponUsageId);

        assertThat(result).isEqualTo(categoryIds);
    }
}