package com.anngorithm.tabling.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "reserve")
@Getter
@Setter
@NoArgsConstructor
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name; // 가게 이름
    private String customername; // 고객 이름
    private String confirm; // 예약 승인 여부
    private String arriving; // 방문 여부
    private LocalDate date; // 날짜
    private LocalTime time; // 시간
}
