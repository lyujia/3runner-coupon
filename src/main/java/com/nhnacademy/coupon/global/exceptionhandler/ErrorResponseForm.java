package com.nhnacademy.coupon.global.exceptionhandler;

import lombok.Builder;

@Builder
public record ErrorResponseForm(String title, int status, String timestamp) {
}