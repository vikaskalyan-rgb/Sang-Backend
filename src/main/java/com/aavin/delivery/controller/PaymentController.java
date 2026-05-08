package com.aavin.delivery.controller;
import com.aavin.delivery.dto.*;
import com.aavin.delivery.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;
    public PaymentController(PaymentService service) { this.service = service; }

    @GetMapping("/customer/{customerId}") public ResponseEntity<ApiResponse<List<PaymentDTO>>> getByCustomer(@PathVariable Long customerId) { return ResponseEntity.ok(ApiResponse.ok(service.getByCustomer(customerId))); }
    @GetMapping("/paid-ids") public ResponseEntity<ApiResponse<List<Long>>> getPaidIds(@RequestParam int month, @RequestParam int year) { return ResponseEntity.ok(ApiResponse.ok(service.getPaidCustomerIds(month, year))); }
    @PostMapping public ResponseEntity<ApiResponse<PaymentDTO>> create(@Valid @RequestBody PaymentDTO req) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Recorded", service.create(req))); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok(ApiResponse.ok("Deleted", null)); }
}
