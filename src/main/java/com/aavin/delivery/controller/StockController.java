package com.aavin.delivery.controller;
import com.aavin.delivery.dto.*;
import com.aavin.delivery.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/stock")
public class StockController {
    private final StockService service;
    public StockController(StockService service) { this.service = service; }

    @GetMapping public ResponseEntity<ApiResponse<List<StockDTO>>> getAll() { return ResponseEntity.ok(ApiResponse.ok(service.getAllStock())); }
    @GetMapping("/transactions") public ResponseEntity<ApiResponse<List<StockTransactionDTO>>> getTransactions(@RequestParam(defaultValue = "50") int limit) { return ResponseEntity.ok(ApiResponse.ok(service.getTransactions(limit))); }
    @PostMapping("/transaction") public ResponseEntity<ApiResponse<StockDTO>> recordTransaction(@Valid @RequestBody StockTransactionRequest req) { return ResponseEntity.ok(ApiResponse.ok("Updated", service.recordTransaction(req))); }
}
