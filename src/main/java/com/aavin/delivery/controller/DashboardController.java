package com.aavin.delivery.controller;
import com.aavin.delivery.dto.*;
import com.aavin.delivery.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;
    public DashboardController(DashboardService service) { this.service = service; }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardDTO>> getSummary(
            @RequestParam(required = false) String from, @RequestParam(required = false) String to) {
        LocalDate f = from != null ? LocalDate.parse(from) : null;
        LocalDate t = to   != null ? LocalDate.parse(to)   : null;
        return ResponseEntity.ok(ApiResponse.ok(service.getSummary(f, t)));
    }
}
