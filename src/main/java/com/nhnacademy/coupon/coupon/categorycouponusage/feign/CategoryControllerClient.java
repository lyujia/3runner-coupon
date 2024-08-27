package com.nhnacademy.coupon.coupon.categorycouponusage.feign;

import com.nhnacademy.coupon.coupon.categorycouponusage.feign.dto.CategoryForCouponResponse;
import com.nhnacademy.coupon.global.util.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "CategoryControllerClient", url = "http://${feign.bookstore.url}")
public interface CategoryControllerClient {

    @GetMapping("bookstore/categories/list")
    ApiResponse<List<CategoryForCouponResponse>> readAllCategoriesList(@RequestParam List<Long> ids);
}
