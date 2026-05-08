package com.aavin.delivery.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api")
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity<Map<String,String>> health() {
        return ResponseEntity.ok(Map.of("status","UP","service","Aavin Milk Delivery API","version","1.0.0"));
    }
}
