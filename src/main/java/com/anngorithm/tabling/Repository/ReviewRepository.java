package com.anngorithm.tabling.Repository;

import com.anngorithm.tabling.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;

@Transactional
@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    Review getByNameAndDateAndTime(String name, LocalDate date, LocalTime time);

    void deleteByName(String name);

    boolean existsByNameAndDateAndTimeAndCustomername(String name, LocalDate date, LocalTime time, String customername);
}
