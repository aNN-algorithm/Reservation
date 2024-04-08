package com.anngorithm.tabling.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "market")
@Getter
@Setter
@NoArgsConstructor
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name; // 매장 이름 (이 프로젝트 소스 내 name은 모두 가게 이름으로 간주)
    private String location; // 매장 위치
    private String text; // 매장 상세 정보

    private String partnername; // 매장 점장 이름
}
