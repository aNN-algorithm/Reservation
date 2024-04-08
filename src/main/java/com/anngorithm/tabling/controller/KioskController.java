package com.anngorithm.tabling.controller;

import com.anngorithm.tabling.service.KioskService;
import org.springframework.web.bind.annotation.*;

@RestController
public class KioskController {

    private final KioskService kioskService;

    public KioskController(KioskService kioskService) {
        this.kioskService = kioskService;
    }

    @PostMapping("/kiosk/arrive")
    public void kioskArrive(@RequestParam String name, String customerName) {

        kioskService.kisokArrive(name, customerName);
    }
}
