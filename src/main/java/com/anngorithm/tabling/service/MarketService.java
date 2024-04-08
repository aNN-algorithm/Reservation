package com.anngorithm.tabling.service;

import com.anngorithm.tabling.Repository.MarketRepository;
import com.anngorithm.tabling.domain.entity.Market;
import org.springframework.stereotype.Service;

@Service
public class MarketService {
    private final MarketRepository marketRepository;

    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    // 매장 정보 등록
    public void createMarket(String name, String location, String text, String partnerName) {
        boolean exists = marketRepository.existsByName(name);
        if (exists) {
            throw new RuntimeException("이미 존재하는 매장입니다.");
        }
        Market newMarket = new Market();
        newMarket.setName(name);
        newMarket.setLocation(location);
        newMarket.setText(text);
        newMarket.setPartnername(partnerName);
        marketRepository.save(newMarket);
    }

    // 매장 정보 수정
    public void updateMarket(String preName, String name, String location, String text, String partnerName) {
        Market nowMarket = marketRepository.getByName(preName);
        if (!nowMarket.getPartnername().equals(partnerName)) {
            throw new RuntimeException("해당 가게의 점장이 아닙니다.");
        }

        // 업데이트할 정보 셋팅
        nowMarket.setName(name);
        if (!location.isEmpty()) {
            nowMarket.setLocation(location); // 비어있으면 기존 위치 저장
        }
        nowMarket.setText(text); // 비어있지 않게 Controller에서 처리
        marketRepository.save(nowMarket);
    }

    public void deleteMarket(String name) {
        marketRepository.deleteByName(name);
    }
}
