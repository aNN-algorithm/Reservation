package com.anngorithm.tabling.service;

import com.anngorithm.tabling.Repository.MarketRepository;
import com.anngorithm.tabling.Repository.ReserveRepository;
import com.anngorithm.tabling.domain.entity.Market;
import com.anngorithm.tabling.domain.entity.Reserve;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReserveService {

    private final ReserveRepository reserveRepository;
    private final MarketRepository marketRepository;

    public ReserveService(ReserveRepository reserveRepository, MarketRepository marketRepository) {
        this.reserveRepository = reserveRepository;
        this.marketRepository = marketRepository;
    }

    // 예약 신청
    public Reserve createReserve(String name, LocalDate date, LocalTime time, String customerName) {

        // 예약 중복 확인
        if (viewReservation(name, date, time) != null) {
            throw new RuntimeException("해당 날짜 및 시간은 예약이 불가합니다.");
        }

        // 예약 정보 저장
        Reserve newReserve = new Reserve();
        newReserve.setName(name);
        newReserve.setConfirm("Approving");
        newReserve.setCustomername(customerName);
        newReserve.setArriving("NO");
        newReserve.setDate(date);
        newReserve.setTime(time);
        return reserveRepository.save(newReserve);
    }

    // 예약 존재 여부 확인
    public Reserve viewReservation(String name, LocalDate date, LocalTime time) {
        Reserve reserve = reserveRepository.getByNameAndDateAndTime(name, date, time);

        if (reserve == null) {
            return null;
        }

        return reserve;
    }

    // 예약 승인 (점장)
    public void confirmReservation(String name, LocalDate date, LocalTime time, String partnerName) {
        Reserve nowReserve = viewReservation(name, date, time);
        if (nowReserve == null) {
            throw new RuntimeException("예약이 없습니다.");
        }
        Market nowMarket = marketRepository.getByName(nowReserve.getName());

        // 본인 가게인지 확인
        if (!nowMarket.getPartnername().equals(partnerName)) {
            throw new RuntimeException("해당 가게의 점장이 아닙니다.");
        }

        nowReserve.setConfirm("Fixed");
        reserveRepository.save(nowReserve);
    }

    // 예약 거절 (점장)
    public void refuseReservation(String name, LocalDate date, LocalTime time, String partnerName) {
        Reserve nowReserve = viewReservation(name, date, time);
        if (nowReserve == null) {
            throw new RuntimeException("예약이 없습니다.");
        }
        Market nowMarket = marketRepository.getByName(nowReserve.getName());

        if (!nowMarket.getPartnername().equals(partnerName)) {
            throw new RuntimeException("해당 가게의 점장이 아닙니다.");
        }

        nowReserve.setConfirm("Refused");
        reserveRepository.save(nowReserve);
    }

    // 점장의 날짜별 예약 리스트 확인
    public List<Reserve> listReservation(String name, LocalDate date, String partnerName) {
        Market market = marketRepository.getByName(name);

        if (!market.getPartnername().equals(partnerName)) {
            throw new RuntimeException("점장 확인이 실패했습니다.");
        }

        return reserveRepository.findAllByNameAndDate(name, date);
    }
}
