package com.nhnacademy.coupon.coupon.couponform.repository;

import com.nhnacademy.coupon.entity.couponform.CouponForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * JPA 쿠폰폼 저장소.
 *
 * @author 김병우
 */
public interface CouponFormRepository extends JpaRepository<CouponForm, Long> {
    /**
     * 만료일이 오늘인 쿠폰 읽기.
     *
     * @return 쿠폰폼 리스트
     */
    @Query(value = "SELECT * FROM coupon_form WHERE DATE(end_date) = CURRENT_DATE", nativeQuery = true)
    List<CouponForm> findCouponsExpiringToday();

    /**
     * 만료일이 3일 남은 쿠폰 읽기.
     *
     * @return 쿠폰폼리스트
     */
    @Query(value = "SELECT * FROM coupon_form WHERE DATE(end_date) = DATE_ADD(CURRENT_DATE, INTERVAL 3 DAY)", nativeQuery = true)
    List<CouponForm> findCouponsExpiringThreeDaysLater();


    List<CouponForm> findAllByIdIn(List<Long> couponFormIds);
}
