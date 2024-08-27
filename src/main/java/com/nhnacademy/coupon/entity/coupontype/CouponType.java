package com.nhnacademy.coupon.entity.coupontype;

import com.nhnacademy.coupon.entity.couponform.CouponForm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CouponType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "couponType" , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<CouponForm> couponForms = new ArrayList<>();

    public CouponType(String type) {
        this.type = type;
    }
}
