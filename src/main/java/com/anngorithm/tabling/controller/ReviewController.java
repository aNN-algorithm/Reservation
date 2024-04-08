package com.anngorithm.tabling.controller;

import com.anngorithm.tabling.service.ReviewService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 리뷰 작성
    @PostMapping("/create/review")
    @PreAuthorize("hasRole('USER')")
    public void createReview(@RequestParam String name,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                             @RequestBody String text,
                             @Validated Authentication authentication) {
        String customername = authentication.getName();
        reviewService.createReview(name, text, date, time, customername);
    }

    // 리뷰 수정
    @PostMapping("/update/review")
    @PreAuthorize("hasRole('USER')")
    public void updateReview(@RequestParam String name,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                             @RequestBody String text,
                             @Validated Authentication authentication) {
        String customername = authentication.getName();
        reviewService.updateReview(name, text, date, time, customername);
    }

    // 리뷰 삭제
    @DeleteMapping("/delete/review")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void deleteReview(@RequestParam String name,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime time,
                             @Validated Authentication authentication) {
        String signinname = authentication.getName();
        reviewService.deleteReview(name, date, time, signinname);
    }
}
