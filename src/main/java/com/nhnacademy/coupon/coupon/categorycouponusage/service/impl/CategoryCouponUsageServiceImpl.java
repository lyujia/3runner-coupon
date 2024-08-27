package com.nhnacademy.coupon.coupon.categorycouponusage.service.impl;

import com.nhnacademy.coupon.coupon.categorycoupon.repository.CategoryCouponRepository;
import com.nhnacademy.coupon.coupon.categorycouponusage.feign.CategoryControllerClient;
import com.nhnacademy.coupon.coupon.categorycouponusage.feign.dto.CategoryForCouponResponse;
import com.nhnacademy.coupon.coupon.categorycouponusage.repository.CategoryCouponUsageRepository;
import com.nhnacademy.coupon.coupon.categorycouponusage.service.CategoryCouponUsageService;
import com.nhnacademy.coupon.coupon.couponusage.repository.CouponUsageRespository;
import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
import com.nhnacademy.coupon.entity.categorycouponusage.CategoryCouponUsage;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 카테고리 쿠폰 사용처 서비스 구현체.
 *
 * @author 김병우
 */
@Transactional
@Service
@RequiredArgsConstructor
public class CategoryCouponUsageServiceImpl implements CategoryCouponUsageService {
    private final CouponUsageRespository couponUsageRespository;
    private final CategoryCouponUsageRepository categoryCouponUsageRepository;
    private final CategoryCouponRepository categoryCouponRepository;
    private final CategoryControllerClient categoryControllerClient;

    @Override
    public Long create(List<Long> categoryIds) {
        StringBuilder usage = new StringBuilder("사용가능 카테고리 : ");

        List<CategoryForCouponResponse> categorys = categoryControllerClient.readAllCategoriesList(categoryIds).getBody().getData();

        for(CategoryForCouponResponse category : categorys){
            usage.append(category.name()).append(",");
        }

        CouponUsage couponUsage = new CouponUsage(usage.toString());
        couponUsageRespository.save(couponUsage);

        for (Long l : categoryIds) {
            Optional<CategoryCoupon> categoryCouponOptional = categoryCouponRepository.findById(l);
            CategoryCoupon categoryCoupon;

            if(categoryCouponOptional.isEmpty()){
                categoryCoupon = new CategoryCoupon(l);
                categoryCouponRepository.save(categoryCoupon);
            } else {
                categoryCoupon = categoryCouponOptional.get();
            }

            CategoryCouponUsage categoryCouponUsage = new CategoryCouponUsage(couponUsage, categoryCoupon);

            categoryCouponUsageRepository.save(categoryCouponUsage);
        }

        return couponUsage.getId();
    }

    @Override
    public List<Long> readCategorys(Long couponUsageId) {
        return categoryCouponUsageRepository.findCategoryIdsByCouponUsageId(couponUsageId);
    }
}
