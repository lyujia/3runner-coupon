package com.nhnacademy.coupon.global.exceptionhandler;

import java.nio.channels.AlreadyBoundException;
import java.time.ZonedDateTime;

import com.nhnacademy.coupon.coupon.couponform.exception.CouponFormNotExistException;
import com.nhnacademy.coupon.coupon.coupontype.exception.CouponTypeDoesNotExistException;
import com.nhnacademy.coupon.coupon.couponusage.exception.CouponUsageDoesNotExistException;
import com.nhnacademy.coupon.global.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Error Handler Controller
 * @author 김병우
 */
@RestControllerAdvice
public class WebControllerAdvice {

    // 응답, 성공,실패 폼 맞추기

    /**
     * INTERNAL_SERVER_ERROR 처리 메소드
     *
     * @param ex
     * @param model
     * @return ApiResponse<ErrorResponseForm>
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorResponseForm> runtimeExceptionHandler(Exception ex, Model model) {

        return ApiResponse.fail(500,
                new ApiResponse.Body<>(
                        ErrorResponseForm.builder()
                                .title(ex.getMessage())
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .timestamp(ZonedDateTime.now().toString())
                                .build())
        );
    }

    /**
     * BAD_REQUEST 처리 메소드
     *
     * @param ex
     * @param model
     * @return ApiResponse<ErrorResponseForm>
     */
    @ExceptionHandler({
            CouponTypeDoesNotExistException.class,
            CouponUsageDoesNotExistException.class,
            CouponFormNotExistException.class,
            AlreadyBoundException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponseForm> badRequestHandler(Exception ex, Model model) {
        return ApiResponse.badRequestFail(
                new ApiResponse.Body<>(ErrorResponseForm.builder()
                        .title(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(ZonedDateTime.now().toString())
                        .build())
        );

    }
}