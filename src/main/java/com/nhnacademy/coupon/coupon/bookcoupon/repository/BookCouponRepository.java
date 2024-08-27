package com.nhnacademy.coupon.coupon.bookcoupon.repository;

import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookCouponRepository extends JpaRepository<BookCoupon, Long> {
}
