package com.nhnacademy.coupon.entity.couponusage;

import com.nhnacademy.coupon.entity.bookcouponusage.BookCouponUsage;
import com.nhnacademy.coupon.entity.categorycouponusage.CategoryCouponUsage;
import com.nhnacademy.coupon.entity.couponform.CouponForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CouponUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usage;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "couponUsage" , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CouponForm> couponForms = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "couponUsage" , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BookCouponUsage> bookCouponUsages = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "couponUsage" , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CategoryCouponUsage> categoryCouponUsages = new ArrayList<>();

    public CouponUsage(String usage) {
        this.usage = usage;
    }
}
