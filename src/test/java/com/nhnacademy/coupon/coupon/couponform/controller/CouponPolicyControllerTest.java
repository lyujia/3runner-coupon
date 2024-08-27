package com.nhnacademy.coupon.coupon.couponform.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.nhnacademy.coupon.coupon.BaseDocumentTest;
import com.nhnacademy.coupon.coupon.bookcouponusage.service.BookCouponUsageService;
import com.nhnacademy.coupon.coupon.categorycouponusage.service.CategoryCouponUsageService;
import com.nhnacademy.coupon.coupon.coupontype.dto.response.ReadCouponTypeResponse;
import com.nhnacademy.coupon.coupon.coupontype.service.CouponTypeService;
import com.nhnacademy.coupon.coupon.couponusage.dto.response.ReadCouponUsageResponse;
import com.nhnacademy.coupon.coupon.couponusage.service.CouponUsageService;
import com.nhnacademy.coupon.coupon.fixedcoupon.dto.response.ReadFixedCouponResponse;
import com.nhnacademy.coupon.coupon.fixedcoupon.service.FixedCouponService;
import com.nhnacademy.coupon.coupon.ratiocoupon.dto.response.ReadRatioCouponResponse;
import com.nhnacademy.coupon.coupon.ratiocoupon.service.RatioCouponService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(
        controllers = {
                CouponPolicyController.class
        }
)
class CouponPolicyControllerTest extends BaseDocumentTest {
    @MockBean
    private BookCouponUsageService bookCouponUsageService;

    @MockBean
    private CategoryCouponUsageService categoryCouponUsageService;

    @MockBean
    private FixedCouponService fixedCouponService;

    @MockBean
    private RatioCouponService ratioCouponService;

    @MockBean
    private CouponTypeService couponTypeService;

    @MockBean
    private CouponUsageService couponUsageService;

    @Test
    void testCreateFixedCouponPolicy() throws Exception {
        String requestBody = "{\"discountPrice\": 1000}";

        given(fixedCouponService.create(anyInt())).willReturn(1L);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/types/fixes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
                        "쿠폰 정책 고정 쿠폰을 생성하는 API",
                        requestFields(
                                fieldWithPath("discountPrice").description("고정할인가")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
                                fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("body.data").type(JsonFieldType.NUMBER).description("쿠폰 아이디")
                        )
                ));
    }

    @Test
    void testCreateRatioCouponPolicy() throws Exception {
        String requestBody = "{\"discountRate\": 0.1, \"discountMaxPrice\": 1000}";

        given(ratioCouponService.create(anyDouble(),anyInt())).willReturn(1L);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/types/ratios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
                        "쿠폰 정책 비율 쿠폰을 생성하는 API",
                        requestFields(
                                fieldWithPath("discountRate").description("할인비율"),
                                fieldWithPath("discountMaxPrice").description("할인최대가")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
                                fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("body.data").type(JsonFieldType.NUMBER).description("쿠폰 아이디")
                        )
                ));
    }
    @Test
    void testCreateCategoryCouponPolicy() throws Exception {
        String requestBody = "{\"categoryIds\": [1, 2, 3]}";

        given(categoryCouponUsageService.create(any(List.class))).willReturn(1L);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/usages/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
                        "카테고리 쿠폰 사용처 정책 쿠폰을 생성하는 API",
                        requestFields(
                                fieldWithPath("categoryIds").description("사용가능 카테고리 아이디 리스트")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
                                fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("body.data").type(JsonFieldType.NUMBER).description("쿠폰 아이디")
                        )
                ));
    }

    @Test
    void testCreateBookCouponPolicy() throws Exception {
        String requestBody = "{\"bookIds\": [1, 2, 3]}";

        given(bookCouponUsageService.create(any(List.class))).willReturn(1L);

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/usages/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
                        "북 쿠폰 사용처 정책 쿠폰을 생성하는 API",
                        requestFields(
                                fieldWithPath("bookIds").description("사용가능 북 아이디 리스트")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
                                fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("body.data").type(JsonFieldType.NUMBER).description("쿠폰 아이디")
                        )
                ));
    }

    @Test
    void testReadAllTypes() throws Exception {
        List<ReadCouponTypeResponse> responses = Arrays.asList(
                new ReadCouponTypeResponse(1L, "Fixed Coupon"),
                new ReadCouponTypeResponse(2L, "Ratio Coupon")
        );

        when(couponTypeService.readAll()).thenReturn(responses);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/types"))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
                        "타입 조회 API",
                        responseFields(
                                fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
                                fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("body.data[].couponTypeId").type(JsonFieldType.NUMBER).description("쿠폰 타입 아이디"),
                                fieldWithPath("body.data[].type").type(JsonFieldType.STRING).description("쿠폰 타입 이름")
                        )
                ));
    }

    @Test
    void testReadFixedType() throws Exception {
        ReadFixedCouponResponse response = new ReadFixedCouponResponse(1L, 1L, 1000);

        when(fixedCouponService.read(1L)).thenReturn(response);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/types/fixes/{couponTypeId}", 1L))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
                        "고정 타입 쿠폰 조회 API",
                        responseFields(
                                fieldWithPath("header.resultCode").description("결과 코드"),
                                fieldWithPath("header.successful").description("성공 여부"),
                                fieldWithPath("body.data.fixedCouponId").description("쿠폰 아이디"),
                                fieldWithPath("body.data.couponTypeId").description("타입 아이디"),
                                fieldWithPath("body.data.discountPrice").description("할인가")
                        )
                ));
    }

    @Test
    void testReadRatioType() throws Exception {
        ReadRatioCouponResponse response = new ReadRatioCouponResponse(1L, 1L, 0.1, 1000);

        when(ratioCouponService.read(1L)).thenReturn(response);


        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/types/ratios/{couponTypeId}", 1L))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
                        "비율 타입 쿠폰 조회 API",
                        responseFields(
                                fieldWithPath("header.resultCode").description("결과 코드"),
                                fieldWithPath("header.successful").description("성공 여부"),
                                fieldWithPath("body.data.ratioCouponId").description("비율 쿠폰 아이디"),
                                fieldWithPath("body.data.couponTypeId").description("쿠폰 타입 아이디"),
                                fieldWithPath("body.data.discountRate").description("할인율"),
                                fieldWithPath("body.data.discountMaxPrice").description("최대 할인가")
                        )
                ));
    }

    @Test
    void testReadAllUsages() throws Exception {
        List<ReadCouponUsageResponse> responses = Arrays.asList(
                new ReadCouponUsageResponse(1L, "Usage 1"),
                new ReadCouponUsageResponse(2L, "Usage 2")
        );

        when(couponUsageService.readAll()).thenReturn(responses);


        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/usages"))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
                        "사용처 조회 API",
                        responseFields(
                                fieldWithPath("header.resultCode").description("결과코드"),
                                fieldWithPath("header.successful").description("성공여부"),
                                fieldWithPath("body.data[].couponUsageId").description("사용처 아이디"),
                                fieldWithPath("body.data[].usage").description("사용처 이름")
                        )
                ));
    }

    @Test
    void testReadCategoryUsages() throws Exception {
        List<Long> categoryIds = Arrays.asList(1L, 2L);

        when(categoryCouponUsageService.readCategorys(1L)).thenReturn(categoryIds);


        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/usages/categories/{couponTypeId}", 1L))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
                        "카테고리 사용처 조회 API",
                        pathParameters(
                                parameterWithName("couponTypeId").description("쿠폰타입아이디")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("결과코드"),
                                fieldWithPath("header.successful").description("성공여부"),
                                fieldWithPath("body.data[]").description("사용 가능 카테고리 쿠폰 아아디 리스트")
                        )
                ));
    }

    @Test
    void testReadBookUsages() throws Exception {
        List<Long> bookIds = Arrays.asList(1L, 2L);

        when(bookCouponUsageService.readBooks(1L)).thenReturn(bookIds);

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/usages/books/{couponTypeId}", 1L))
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
                        "북 사용처 조회 API",
                        pathParameters(
                                parameterWithName("couponTypeId").description("쿠폰타입아이디")
                        ),
                        responseFields(
                                fieldWithPath("header.resultCode").description("결과코드"),
                                fieldWithPath("header.successful").description("성공여부"),
                                fieldWithPath("body.data[]").description("사용가능 북 아이디 리스트")
                        )
                ));
    }
}
