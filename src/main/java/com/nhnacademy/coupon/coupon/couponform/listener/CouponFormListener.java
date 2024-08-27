package com.nhnacademy.coupon.coupon.couponform.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 쿠폰 메시지 큐 리스너.
 *
 * @author 김병우
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CouponFormListener {
    private static final String QUEUE_NAME_1 = "3RUNNER-COUPON-ISSUED";
    private final CouponFormService couponFormService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = QUEUE_NAME_1)
    public void receiveMessage(String createCouponFormRequestJson) throws JsonProcessingException {
        log.info("{}",createCouponFormRequestJson);
        couponFormService.create(objectMapper.readValue(createCouponFormRequestJson, CreateCouponFormRequest.class));
    }
}
