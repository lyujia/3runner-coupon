package com.nhnacademy.coupon.coupon.couponform.controller;

import com.nhnacademy.coupon.coupon.bookcouponusage.service.BookCouponUsageService;
import com.nhnacademy.coupon.coupon.categorycouponusage.service.CategoryCouponUsageService;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateBookCouponRequest;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCategoryCouponRequest;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateFixedCouponRequest;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateRatioCouponRequest;
import com.nhnacademy.coupon.coupon.coupontype.dto.response.ReadCouponTypeResponse;
import com.nhnacademy.coupon.coupon.coupontype.service.CouponTypeService;
import com.nhnacademy.coupon.coupon.couponusage.dto.response.ReadCouponUsageResponse;
import com.nhnacademy.coupon.coupon.couponusage.service.CouponUsageService;
import com.nhnacademy.coupon.coupon.fixedcoupon.dto.response.ReadFixedCouponResponse;
import com.nhnacademy.coupon.coupon.fixedcoupon.service.FixedCouponService;
import com.nhnacademy.coupon.coupon.ratiocoupon.dto.response.ReadRatioCouponResponse;
import com.nhnacademy.coupon.coupon.ratiocoupon.service.RatioCouponService;
import com.nhnacademy.coupon.global.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class CouponPolicyController {
    private final BookCouponUsageService bookCouponUsageService;
    private final CategoryCouponUsageService categoryCouponUsageService;
    private final FixedCouponService fixedCouponService;
    private final RatioCouponService ratioCouponService;

    private final CouponTypeService couponTypeService;
    private final CouponUsageService couponUsageService;

    @PostMapping("/types/fixes")
    public ApiResponse<Long> createFixedCouponPolicy(@RequestBody CreateFixedCouponRequest createFixedCouponRequest) {
        Long couponTypeId = fixedCouponService.create(createFixedCouponRequest.discountPrice());

        return ApiResponse.createSuccess(couponTypeId);
    }

    @PostMapping("/types/ratios")
    public ApiResponse<Long> createRatioCouponPolicy(@RequestBody CreateRatioCouponRequest createRatioCouponRequest) {

        Long couponTypeId = ratioCouponService.create(
                createRatioCouponRequest.discountRate(),
                createRatioCouponRequest.discountMaxPrice()
        );

        return ApiResponse.createSuccess(couponTypeId);
    }

    @PostMapping("/usages/categories")
    public ApiResponse<Long> createCategoryCouponPolicy(@RequestBody CreateCategoryCouponRequest createCategoryCouponRequest) {
        return ApiResponse.createSuccess(categoryCouponUsageService.create(createCategoryCouponRequest.categoryIds()));
    }

    @PostMapping("/usages/books")
    public ApiResponse<Long> createBookCouponPolicy(@RequestBody CreateBookCouponRequest createBookCouponRequest){
        return ApiResponse.createSuccess(bookCouponUsageService.create(createBookCouponRequest.bookIds()));
    }

    @GetMapping("/types")
    public ApiResponse<List<ReadCouponTypeResponse>> readAllTypes(){
        return ApiResponse.success(couponTypeService.readAll());
    }

    @GetMapping("/types/fixes/{couponTypeId}")
    public ApiResponse<ReadFixedCouponResponse> readFixedType(@PathVariable Long couponTypeId){
        return ApiResponse.success(fixedCouponService.read(couponTypeId));
    }

    @GetMapping("/types/ratios/{couponTypeId}")
    public ApiResponse<ReadRatioCouponResponse> readRatioType(@PathVariable Long couponTypeId){
        return ApiResponse.success(ratioCouponService.read(couponTypeId));
    }

    @GetMapping("/usages")
    public ApiResponse<List<ReadCouponUsageResponse>> readAllUsages(){
        return ApiResponse.success(couponUsageService.readAll());
    }

    @GetMapping("/usages/categories/{couponTypeId}")
    public ApiResponse<List<Long>> readCategoryUsages(@PathVariable Long couponTypeId){
        return ApiResponse.success(categoryCouponUsageService.readCategorys(couponTypeId));
    }

    @GetMapping("/usages/books/{couponTypeId}")
    public ApiResponse<List<Long>> readBookUsages(@PathVariable Long couponTypeId){
        return ApiResponse.success(bookCouponUsageService.readBooks(couponTypeId));
    }
}
