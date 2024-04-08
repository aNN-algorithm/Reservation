package com.anngorithm.tabling.service;

import com.anngorithm.tabling.Repository.ReserveRepository;
import com.anngorithm.tabling.domain.entity.Reserve;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
public class KioskService {

    private final ReserveRepository reserveRepository;

    public KioskService(ReserveRepository reserveRepository) {
        this.reserveRepository = reserveRepository;
    }

    // 키오스크 방문
    public Reserve kisokArrive(String name, String customerName) {
        LocalDate nowDate = LocalDate.now();

        // 예약 유무 확인
        Reserve reserve = reserveRepository.getByNameAndDateAndCustomername(name, nowDate, customerName);
        if (reserve == null) {
            throw new RuntimeException("예약이 없습니다.");
        }

        // 예약 승인 유무 확인
        if (!reserve.getConfirm().equals("Fixed")) {
            throw new RuntimeException("예약이 확정되지 않았습니다.");
        }

        LocalTime nowTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String time = nowTime.format(formatter);
        String reserveTime = reserve.getTime().format(formatter);

        long minutesBetween = ChronoUnit.MINUTES.between(reserve.getTime(), nowTime);

        if (minutesBetween > -10) {
            throw new RuntimeException("방문 확인을 진행할 수 없습니다.");
        }

        reserve.setArriving("YES");
        reserveRepository.save(reserve);

        return reserve;
    }
}
