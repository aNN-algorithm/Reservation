package com.anngorithm.tabling.service;

import com.anngorithm.tabling.Repository.MarketRepository;
import com.anngorithm.tabling.Repository.ReserveRepository;
import com.anngorithm.tabling.Repository.ReviewRepository;
import com.anngorithm.tabling.domain.entity.Market;
import com.anngorithm.tabling.domain.entity.Reserve;
import com.anngorithm.tabling.domain.entity.Review;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReserveRepository reserveRepository;
    private final MarketRepository marketRepository;

    public ReviewService(ReviewRepository reviewRepository, ReserveRepository reserveRepository,
                         MarketRepository marketRepository) {
        this.reviewRepository = reviewRepository;
        this.reserveRepository = reserveRepository;
        this.marketRepository = marketRepository;
    }

    // 리뷰 작성
    public void createReview(String name, String text, LocalDate date, LocalTime time, String customername) {
        Reserve reserve = reserveRepository.getByNameAndDateAndTime(name, date, time);

        // 예약 정보 확인
        if (reserve == null) {
            throw new RuntimeException("해당 날짜에 예약이 없습니다.");
        }

        // 방문 확인
        if (!reserve.getArriving().equals("YES")) {
            throw new RuntimeException("해당 예약에 방문하지 않았습니다.");
        }

        if (!reserve.getCustomername().equals(customername)) {
            throw new RuntimeException("해당 예약자가 아닙니다.");
        }

        // 이미 작성된 리뷰 유무 확인
        if (reviewRepository.existsByNameAndDateAndTimeAndCustomername(name, date, time, customername)) {
            throw new RuntimeException("이미 작성된 리뷰가 있습니다.");
        }

        Review newReview = new Review();
        newReview.setName(name);
        newReview.setText(text);
        newReview.setDate(date);
        newReview.setTime(time);
        newReview.setCustomername(customername);
        reviewRepository.save(newReview);
    }

    // 리뷰 수정
    public void updateReview(String name, String text, LocalDate date, LocalTime time, String customername) {
        Reserve reserve = reserveRepository.getByNameAndDateAndTime(name, date, time);

        // 작성자 확인
        if (!reserve.getCustomername().equals(customername)) {
            throw new RuntimeException("해당 리뷰의 작성자가 아닙니다.");
        }

        Review nowReview = reviewRepository.getByNameAndDateAndTime(name, date, time);
        nowReview.setText(text);
        reviewRepository.save(nowReview);
    }

    // 리뷰 삭제
    public void deleteReview(String name, LocalDate date, LocalTime time, String signinname) {
        Review nowReview = reviewRepository.getByNameAndDateAndTime(name, date, time);
        String customername = nowReview.getCustomername();

        // 리뷰의 작성자나 해당 가게의 점장인지 확인
        if (!customername.equals(signinname)) {
            Market nowMarket = marketRepository.getByName(name);
            String partnername = nowMarket.getPartnername();

            if (!partnername.equals(signinname)) {
                throw new RuntimeException("삭제할 권한이 없는 이용자입니다.");
            } else {
                reviewRepository.deleteByName(name);
            }
        } else {
            reviewRepository.deleteByName(name);
        }
    }
}
