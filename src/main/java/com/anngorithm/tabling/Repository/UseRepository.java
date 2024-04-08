package com.anngorithm.tabling.Repository;

import com.anngorithm.tabling.domain.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UseRepository extends JpaRepository<Market, Integer> {
    Market getByName(String name);
}
