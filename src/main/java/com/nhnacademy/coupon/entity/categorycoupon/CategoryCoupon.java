package com.nhnacademy.coupon.entity.categorycoupon;

import com.nhnacademy.coupon.entity.categorycouponusage.CategoryCouponUsage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CategoryCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * categoryId - bookstore 카테고리에 매핑되는 칼럼
     */
    private Long categoryId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoryCoupon" , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CategoryCouponUsage> categoryCouponUsages = new ArrayList<>();

    public CategoryCoupon(Long categoryId) {
        this.categoryId = categoryId;
    }
}
