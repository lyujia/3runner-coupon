package com.nhnacademy.coupon.entity.categorycouponusage;

import com.nhnacademy.coupon.entity.categorycoupon.CategoryCoupon;
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
public class CategoryCouponUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private CouponUsage couponUsage;

    @ManyToOne
    private CategoryCoupon categoryCoupon;

    public CategoryCouponUsage(CouponUsage couponUsage, CategoryCoupon categoryCoupon) {
        this.couponUsage = couponUsage;
        this.categoryCoupon = categoryCoupon;
    }
}
