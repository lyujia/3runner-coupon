package com.nhnacademy.coupon.coupon.couponform.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.bookcouponusage.service.BookCouponUsageService;
import com.nhnacademy.coupon.coupon.categorycouponusage.service.CategoryCouponUsageService;
import com.nhnacademy.coupon.coupon.couponform.dto.CouponFormDto;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.couponform.repository.CouponFormRepository;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import com.nhnacademy.coupon.coupon.coupontype.exception.CouponTypeDoesNotExistException;
import com.nhnacademy.coupon.coupon.coupontype.repository.CouponTypeRepository;
import com.nhnacademy.coupon.coupon.couponusage.exception.CouponUsageDoesNotExistException;
import com.nhnacademy.coupon.coupon.couponusage.repository.CouponUsageRespository;
import com.nhnacademy.coupon.coupon.fixedcoupon.service.FixedCouponService;
import com.nhnacademy.coupon.coupon.ratiocoupon.service.RatioCouponService;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * 쿠폰폼 서비스 구현체.
 *
 * @author 김병우
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CouponFormServiceImpl implements CouponFormService {
    private final CouponUsageRespository couponUsageRespository;
    private final CouponTypeRepository couponTypeRepository;
    private final CouponFormRepository couponFormRepository;
    private final BookCouponUsageService bookCouponUsageService;
    private final CategoryCouponUsageService categoryCouponUsageService;
    private final FixedCouponService fixedCouponService;
    private final RatioCouponService ratioCouponService;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private static final String QUEUE_NAME_2 = "3RUNNER-COUPON-EXPIRED-IN-THREE-DAY";
    private static final String QUEUE_NAME_1 = "3RUNNER-COUPON-ISSUED";

    @Override
    public Long create(CreateCouponFormRequest createCouponFormRequest) {
        CouponUsage couponUsage = couponUsageRespository
                .findById(createCouponFormRequest.couponUsageId())
                .orElseThrow(
                        ()-> new CouponUsageDoesNotExistException(createCouponFormRequest.couponUsageId()+"가 존재하지 않습니다.")
                );

        CouponType couponType = couponTypeRepository
                .findById(createCouponFormRequest.couponTypeId())
                .orElseThrow(
                        ()-> new CouponTypeDoesNotExistException(createCouponFormRequest.couponTypeId()+"가 존재하지 않습니다.")
                );

        CouponForm couponForm = new CouponForm();

        couponForm.setBasicDetails(
                createCouponFormRequest.startDate(),
                createCouponFormRequest.endDate(),
                createCouponFormRequest.name(),
                UUID.randomUUID(),
                createCouponFormRequest.maxPrice(),
                createCouponFormRequest.minPrice()
        );

        couponForm.setCouponDetails(
                couponType,
                couponUsage
        );

        couponFormRepository.save(couponForm);
        return couponForm.getId();
    }

    @Override
    public CouponForm read(Long couponFormId) {
        return couponFormRepository.findById(couponFormId).orElseThrow(()->new CouponFormNotExistException(""));
    }

    @Override
    public List<ReadCouponFormResponse> readAll(List<Long> couponFormIds) {
        List<CouponForm> couponForms = couponFormRepository.findAllByIdIn(couponFormIds);

        return couponForms.stream()
                .map(couponForm -> ReadCouponFormResponse.builder()
                        .couponFormId(couponForm.getId())
                        .startDate(couponForm.getStartDate())
                        .endDate(couponForm.getEndDate())
                        .createdAt(couponForm.getCreatedAt())
                        .name(couponForm.getName())
                        .code(couponForm.getCode())
                        .maxPrice(couponForm.getMaxPrice())
                        .minPrice(couponForm.getMinPrice())
                        .couponTypeId(couponForm.getCouponType().getId())
                        .couponUsageId(couponForm.getCouponUsage().getId())
                        .usage(couponForm.getCouponUsage().getUsage())
                        .type(couponForm.getCouponType().getType())
                        .books(bookCouponUsageService.readBooks(couponForm.getCouponUsage().getId()))
                        .categorys(categoryCouponUsageService.readCategorys(couponForm.getCouponUsage().getId()))
                        .discountPrice(fixedCouponService.read(couponForm.getCouponType().getId()).discountPrice())
                        .discountRate(ratioCouponService.read(couponForm.getCouponType().getId()).discountRate())
                        .discountMax(ratioCouponService.read(couponForm.getCouponType().getId()).discountMaxPrice())
                        .build())
                .toList();
    }

    @Override
    public List<ReadCouponFormResponse> readAllForms() {
        return couponFormRepository.findAll().stream()
                .map(couponForm -> ReadCouponFormResponse.builder()
                        .couponFormId(couponForm.getId())
                        .startDate(couponForm.getStartDate())
                        .endDate(couponForm.getEndDate())
                        .createdAt(couponForm.getCreatedAt())
                        .name(couponForm.getName())
                        .code(couponForm.getCode())
                        .maxPrice(couponForm.getMaxPrice())
                        .minPrice(couponForm.getMinPrice())
                        .couponTypeId(couponForm.getCouponType().getId())
                        .couponUsageId(couponForm.getCouponUsage().getId())
                        .usage(couponForm.getCouponUsage().getUsage())
                        .type(couponForm.getCouponType().getType())
                        .books(bookCouponUsageService.readBooks(couponForm.getCouponUsage().getId()))
                        .categorys(categoryCouponUsageService.readCategorys(couponForm.getCouponUsage().getId()))
                        .discountPrice(fixedCouponService.read(couponForm.getCouponType().getId()).discountPrice())
                        .discountRate(ratioCouponService.read(couponForm.getCouponType().getId()).discountRate())
                        .discountMax(ratioCouponService.read(couponForm.getCouponType().getId()).discountMaxPrice())
                        .build())
                .toList();
    }

    @Override
    @Async
    @Scheduled(cron = "0 0 13 * * ?")
    public void sendNoticeCouponsExpiringThreeDaysLater() throws JsonProcessingException {
        List<CouponForm> couponsExpiringThreeDaysLater = couponFormRepository.findCouponsExpiringThreeDaysLater();

        List<CouponFormDto> data = couponsExpiringThreeDaysLater.stream().map(o-> CouponFormDto.builder()
                .id(o.getId())
                .name(o.getName())
                .build())
                .toList();

        for (CouponForm couponForm : couponsExpiringThreeDaysLater) {
            log.info(couponForm.getName());
            log.info(String.valueOf(couponForm.getId()));
        }

        rabbitTemplate.convertAndSend(QUEUE_NAME_2, objectMapper.writeValueAsString(data));
    }

}
