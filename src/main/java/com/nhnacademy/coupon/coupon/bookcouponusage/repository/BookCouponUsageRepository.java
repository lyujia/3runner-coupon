package com.nhnacademy.coupon.coupon.bookcouponusage.repository;

import com.nhnacademy.coupon.entity.bookcouponusage.BookCouponUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookCouponUsageRepository extends JpaRepository<BookCouponUsage, Long> {
    @Query("SELECT c.bookCoupon.bookId FROM BookCouponUsage c WHERE c.couponUsage.id = :couponUsageId")
    List<Long> findBookIdsByCouponUsageId(@Param("couponUsageId") Long couponUsageId);
}
