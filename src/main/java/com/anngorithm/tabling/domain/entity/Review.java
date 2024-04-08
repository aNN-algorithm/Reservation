package com.anngorithm.tabling.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "review")
@Getter
@Setter
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name; // 매장 이름
    private String text; // 매장 상세 정보
    private String customername; // 고객 이름
    private LocalDate date; // 예약 날짜
    private LocalTime time; // 예약 시간
}
