package com.anngorithm.tabling.Repository;

import com.anngorithm.tabling.domain.entity.Reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
    // 예약 중복 확인용 (가게 이름, 날짜, 시간)
    Reserve getByNameAndDateAndTime(String name, LocalDate date, LocalTime time);

    Reserve getByNameAndDateAndCustomername(String name, LocalDate date, String customername);

    List<Reserve> findAllByNameAndDate(String name, LocalDate date);

}
