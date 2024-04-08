package com.anngorithm.tabling.service;

import com.anngorithm.tabling.Repository.UseRepository;
import com.anngorithm.tabling.domain.entity.Market;
import org.springframework.stereotype.Service;

@Service
public class UseService {

    private final UseRepository useRepository;

    public UseService(UseRepository useRepository) {
        this.useRepository = useRepository;
    }

    // 매장 검새 - 누구나 가능
    public Market readMarket(String name) {
        return useRepository.getByName(name);
    }
}
