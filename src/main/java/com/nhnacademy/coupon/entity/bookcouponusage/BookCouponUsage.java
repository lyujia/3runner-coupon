package com.nhnacademy.coupon.entity.bookcouponusage;

import com.nhnacademy.coupon.entity.bookcoupon.BookCoupon;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class BookCouponUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CouponUsage couponUsage;

    @ManyToOne
    private BookCoupon bookCoupon;

    public BookCouponUsage(CouponUsage couponUsage, BookCoupon bookCoupon) {
        this.couponUsage = couponUsage;
        this.bookCoupon = bookCoupon;
    }
}
