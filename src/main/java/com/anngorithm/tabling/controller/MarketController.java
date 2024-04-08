package com.anngorithm.tabling.controller;

import com.anngorithm.tabling.service.MarketService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class MarketController {
    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    // 매장 정보 등록
    @PostMapping("/create/market")
    @PreAuthorize("hasRole('ADMIN')") // ADMIN 권한의 유저만 가능
    void createMarket(@RequestParam String name, String location,
                      @RequestBody Optional<String> desc,
                      @Validated Authentication authentication) {
        String partnerName = authentication.getName();

        // 필수값 입력 외 예외처리
        if (name.isEmpty()) {
            throw new RuntimeException("매장명을 입력하세요.");
        }
        if (location.isEmpty()) {
            throw new RuntimeException("위치를 입력하세요.");
        }
        if (desc.isEmpty()) {
            throw new RuntimeException("매장 상세정보를 입력하세요.");
        }
        marketService.createMarket(name, location, desc.get(), partnerName);
    }

    // 매장 정보 수정
    @PostMapping("/update/market")
    @PreAuthorize("hasRole('ADMIN')")
    void updateMarket(@RequestParam String preName, String name, String location,
                      @RequestBody Optional<String> desc,
                      @Validated Authentication authentication) {
        String partnerName = authentication.getName();

        // 필수값 입력 외 예외처리
        // 수정할 매장명이 없는 경우, 기존 매장명 유지
        if (name.isEmpty()) {
            name = preName;
        }
        if (desc.isEmpty()) {
            throw new RuntimeException("매장 상세정보를 입력하세요.");
        }

        // market.updateMarket
        marketService.updateMarket(preName, name, location, desc.get(), partnerName);
    }

    // 매장 정보 삭제
    @DeleteMapping("/delete/market")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteMarket(@RequestParam String name) {
        marketService.deleteMarket(name);
    }

}
