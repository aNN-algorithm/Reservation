package com.anngorithm.tabling.Repository;

import com.anngorithm.tabling.domain.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface MarketRepository extends JpaRepository<Market, Integer> {
    Market getByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);
}
