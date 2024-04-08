package com.anngorithm.tabling.controller;

import com.anngorithm.tabling.domain.entity.Reserve;
import com.anngorithm.tabling.service.ReserveService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class ReserveController {

    private final ReserveService reserveService;

    public ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    // 예약 신청
    @GetMapping("/create/reserve")
    @PreAuthorize("hasRole('USER')")
    public Reserve createReserve(@RequestParam String name,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                                 @Validated Authentication authentication) {
        String customerName = authentication.getName();
        return reserveService.createReserve(name, date, time, customerName);
    }

    // 예약 승인 (점장)
    @GetMapping("/confirm/reserve")
    @PreAuthorize("hasRole('ADMIN')")
    public void confirmReservation(@RequestParam String name,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                                   @Validated Authentication authentication) {
        String partnerName = authentication.getName();
        reserveService.confirmReservation(name, date, time, partnerName);
    }

    // 예약 거절 (점장)
    @GetMapping("/refuse/reserve")
    @PreAuthorize("hasRole('ADMIN')")
    public void refuseReservation(@RequestParam String name,
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                                  @Validated Authentication authentication) {
        String partnerName = authentication.getName();
        reserveService.refuseReservation(name, date, time, partnerName);
    }

    // 점장의 날짜별 예약 리스트 확인
    @GetMapping("/list/reserve")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Reserve> listReservation(@RequestParam String name,
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                         @Validated Authentication authentication) {
        String partnerName = authentication.getName();
        return reserveService.listReservation(name, date, partnerName);
    }
}
