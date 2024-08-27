package com.nhnacademy.coupon.entity.couponform;

import java.time.ZonedDateTime;
import java.util.UUID;


import com.nhnacademy.coupon.entity.coupontype.CouponType;
import com.nhnacademy.coupon.entity.couponusage.CouponUsage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor@Setter
public class CouponForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private ZonedDateTime createdAt;

    private String name;

    private UUID code;
    private Integer maxPrice;
    private Integer minPrice;

    @ManyToOne
    private CouponType couponType;

    @ManyToOne
    private CouponUsage couponUsage;


    @PrePersist
    protected void onCreate() {
        this.createdAt = ZonedDateTime.now();
    }

    public void setBasicDetails(ZonedDateTime startDate, ZonedDateTime endDate, String name, UUID code, Integer maxPrice, Integer minPrice) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.code = code;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    // 두 번째 set 함수
    public void setCouponDetails(CouponType couponType, CouponUsage couponUsage) {
        this.couponType = couponType;
        this.couponUsage = couponUsage;
    }
}
