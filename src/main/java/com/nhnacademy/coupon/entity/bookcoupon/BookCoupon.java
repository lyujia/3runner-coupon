package com.nhnacademy.coupon.entity.bookcoupon;


import com.nhnacademy.coupon.entity.bookcouponusage.BookCouponUsage;
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
public class BookCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * bookId - bookstore 도서에 매핑되는 아이디
     */
    private Long bookId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bookCoupon" , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BookCouponUsage> bookCouponUsages = new ArrayList<>();

    public BookCoupon(Long bookId) {
        this.bookId = bookId;
    }
}
