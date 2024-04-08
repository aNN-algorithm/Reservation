package com.anngorithm.tabling.controller;

import com.anngorithm.tabling.domain.entity.Market;
import com.anngorithm.tabling.service.UseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UseController {

    private final UseService useService;

    public UseController(UseService useService) {
        this.useService = useService;
    }

    @GetMapping("/read/market")
    Market readMarket(@RequestParam String name) {
        return useService.readMarket(name);
    }
}
