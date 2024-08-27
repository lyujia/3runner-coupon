package com.nhnacademy.coupon.coupon.couponform.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.nhnacademy.coupon.coupon.BaseDocumentTest;
import com.nhnacademy.coupon.coupon.couponform.dto.ReadCouponFormResponse;
import com.nhnacademy.coupon.coupon.couponform.dto.request.CreateCouponFormRequest;
import com.nhnacademy.coupon.coupon.couponform.service.CouponFormService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@WebMvcTest(
		controllers = {
				CouponFormController.class
		}
)
class CouponFormControllerTest extends BaseDocumentTest {
	@MockBean
	private RabbitTemplate rabbitTemplate;

	@MockBean
	private CouponFormService couponFormService;

	@DisplayName("쿠폰 만들기")
	@Test
	void testCreateCouponForm() throws Exception {
		String requestBody = "{"
			+ "\"startDate\": \"2024-01-01T00:00:00Z\","
			+ "\"endDate\": \"2024-12-31T23:59:59Z\","
			+ "\"name\": \"Test Coupon\","
			+ "\"maxPrice\": 1000,"
			+ "\"minPrice\": 500,"
			+ "\"couponTypeId\": 1,"
			+ "\"couponUsageId\": 1"
			+ "}";

		given(couponFormService.create(any(CreateCouponFormRequest.class))).willReturn(1L);

		this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/forms")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isOk())
			.andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
				"쿠폰 폼을 생성하는 API",
				requestFields(
					fieldWithPath("startDate").description("쿠폰 시작일"),
					fieldWithPath("endDate").description("쿠폰 종료일"),
					fieldWithPath("name").description("쿠폰 이름"),
					fieldWithPath("maxPrice").description("최대 금액"),
					fieldWithPath("minPrice").description("최소 금액"),
					fieldWithPath("couponTypeId").description("쿠폰 타입 번호"),
					fieldWithPath("couponUsageId").description("쿠폰 사용 번호")
				),
				responseFields(
					fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
					fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
					fieldWithPath("body.data").type(JsonFieldType.NUMBER).description("쿠폰 아이디")
				)
			));
	}

	@Test
	void testReadAllCouponForms() throws Exception {
		List<ReadCouponFormResponse> responses = Arrays.asList(
			new ReadCouponFormResponse(
				1L, ZonedDateTime.parse("2024-01-01T00:00:00Z"), ZonedDateTime.parse("2024-12-31T23:59:59Z"),
				ZonedDateTime.now(), "Test Coupon 1", UUID.randomUUID(), 1000, 500,
				1L, 1L, "Fixed", "Books", Arrays.asList(1L, 2L), Arrays.asList(1L, 2L),
				1000, 0.0, 0),
			new ReadCouponFormResponse(
				2L, ZonedDateTime.parse("2024-01-01T00:00:00Z"), ZonedDateTime.parse("2024-12-31T23:59:59Z"),
				ZonedDateTime.now(), "Test Coupon 2", UUID.randomUUID(), 2000, 1000,
				2L, 2L, "Ratio", "Categories", Arrays.asList(1L, 2L), Arrays.asList(1L, 2L),
				0, 0.2, 500)
		);

		when(couponFormService.readAllForms()).thenReturn(responses);
		this.mockMvc.perform(RestDocumentationRequestBuilders.get("/coupon/forms"))
				.andExpect(status().isOk())
				.andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
						"쿠폰 폼 조회 API",
						responseFields(
								fieldWithPath("header.resultCode").description("결과코드"),
								fieldWithPath("header.successful").description("성공여부"),
								fieldWithPath("body.data[].couponFormId").description("쿠폰 폼 아이디"),
								fieldWithPath("body.data[].startDate").description("쿠폰 시작일"),
								fieldWithPath("body.data[].endDate").description("쿠폰 만료일"),
								fieldWithPath("body.data[].createdAt").description("쿠폰 생성일"),
								fieldWithPath("body.data[].name").description("쿠폰폼 이름"),
								fieldWithPath("body.data[].code").description("쿠폰폼 코드"),
								fieldWithPath("body.data[].maxPrice").description("쿠폰 적용 최대가"),
								fieldWithPath("body.data[].minPrice").description("쿠폰 적용 최소가"),
								fieldWithPath("body.data[].couponTypeId").description("쿠폰타입 아이디"),
								fieldWithPath("body.data[].couponUsageId").description("쿠폰사용처 아이디"),
								fieldWithPath("body.data[].type").description("쿠폰타입 "),
								fieldWithPath("body.data[].usage").description("쿠폰 사용처 이름"),
								fieldWithPath("body.data[].books").description("북 사용처 리스트"),
								fieldWithPath("body.data[].categorys").description(
										"카테고리 사용처 리스트"),
								fieldWithPath("body.data[].discountPrice").description(
										"고정할인가"),
								fieldWithPath("body.data[].discountRate").description(
										"할인율"),
								fieldWithPath("body.data[].discountMax").description(
										"최대할인가")
						)
				));
	}

	@Test
	void testReadCouponForm() throws Exception {
		String requestBody = "{\"couponFormIds\": [1, 2, 3]}";

		List<ReadCouponFormResponse> responses = Arrays.asList(
			new ReadCouponFormResponse(
				1L, ZonedDateTime.parse("2024-01-01T00:00:00Z"), ZonedDateTime.parse("2024-12-31T23:59:59Z"),
				ZonedDateTime.now(), "Test Coupon 1", UUID.randomUUID(), 1000, 500,
				1L, 1L, "Fixed", "Books", Arrays.asList(1L, 2L), Arrays.asList(1L, 2L),
				1000, 0.0, 0),
			new ReadCouponFormResponse(
				2L, ZonedDateTime.parse("2024-01-01T00:00:00Z"), ZonedDateTime.parse("2024-12-31T23:59:59Z"),
				ZonedDateTime.now(), "Test Coupon 2", UUID.randomUUID(), 2000, 1000,
				2L, 2L, "Ratio", "Categories", Arrays.asList(1L, 2L), Arrays.asList(1L, 2L),
				0, 0.2, 500)
		);

		when(couponFormService.readAll(any())).thenReturn(responses);

		this.mockMvc.perform(RestDocumentationRequestBuilders.post("/coupon/members/forms")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody)
						.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andDo(MockMvcRestDocumentationWrapper.document(snippetPath,
						"쿠폰 폼 조회 API",
						requestFields(
								fieldWithPath("couponFormIds").description("List of coupon form IDs to read")
						),
						responseFields(
								fieldWithPath("header.resultCode").description("결과코드"),
								fieldWithPath("header.successful").description("성공여부"),
								fieldWithPath("body.data[].couponFormId").description("쿠폰 폼 아이디"),
								fieldWithPath("body.data[].startDate").description("쿠폰 시작일"),
								fieldWithPath("body.data[].endDate").description("쿠폰 만료일"),
								fieldWithPath("body.data[].createdAt").description("쿠폰 생성일"),
								fieldWithPath("body.data[].name").description("쿠폰폼 이름"),
								fieldWithPath("body.data[].code").description("쿠폰폼 코드"),
								fieldWithPath("body.data[].maxPrice").description("쿠폰 적용 최대가"),
								fieldWithPath("body.data[].minPrice").description("쿠폰 적용 최소가"),
								fieldWithPath("body.data[].couponTypeId").description("쿠폰타입 아이디"),
								fieldWithPath("body.data[].couponUsageId").description("쿠폰사용처 아이디"),
								fieldWithPath("body.data[].type").description("쿠폰타입 "),
								fieldWithPath("body.data[].usage").description("쿠폰 사용처 이름"),
								fieldWithPath("body.data[].books").description("북 사용처 리스트"),
								fieldWithPath("body.data[].categorys").description(
										"카테고리 사용처 리스트"),
								fieldWithPath("body.data[].discountPrice").description(
										"고정할인가"),
								fieldWithPath("body.data[].discountRate").description(
										"할인율"),
								fieldWithPath("body.data[].discountMax").description(
										"최대할인가")
						)
				));
	}
}
