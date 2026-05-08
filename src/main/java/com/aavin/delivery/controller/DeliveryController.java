package com.aavin.delivery.controller;
import com.aavin.delivery.dto.*;
import com.aavin.delivery.service.DeliveryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController @RequestMapping("/api/deliveries")
public class DeliveryController {
    private final DeliveryService service;
    public DeliveryController(DeliveryService service) { this.service = service; }

    @GetMapping("/today") public ResponseEntity<ApiResponse<List<DailyDeliveryDTO>>> getToday() { return ResponseEntity.ok(ApiResponse.ok(service.getToday())); }
    @PostMapping("/mark") public ResponseEntity<ApiResponse<DailyDeliveryDTO>> markDelivered(@Valid @RequestBody MarkDeliveryRequest req) { return ResponseEntity.ok(ApiResponse.ok("Marked", service.markDelivered(req))); }
    @PostMapping("/unmark/{customerId}") public ResponseEntity<ApiResponse<DailyDeliveryDTO>> unmark(@PathVariable Long customerId, @RequestParam String date) { return ResponseEntity.ok(ApiResponse.ok("Unmarked", service.unmark(customerId, LocalDate.parse(date)))); }
    @PatchMapping("/{customerId}/packets") public ResponseEntity<ApiResponse<DailyDeliveryDTO>> updatePackets(@PathVariable Long customerId, @RequestParam String date, @RequestParam Integer packets) { return ResponseEntity.ok(ApiResponse.ok("Updated", service.updatePackets(customerId, LocalDate.parse(date), packets))); }
    @PostMapping("/bulk-mark-all") public ResponseEntity<ApiResponse<List<DailyDeliveryDTO>>> bulkMarkAll(@Valid @RequestBody BulkMarkRequest req) { return ResponseEntity.ok(ApiResponse.ok("All marked", service.bulkMarkAll(req.getDate(), req.getSubstituteName()))); }
    @GetMapping("/customer/{customerId}") public ResponseEntity<ApiResponse<List<DailyDeliveryDTO>>> getCustomerMonth(@PathVariable Long customerId, @RequestParam int year, @RequestParam int month) { return ResponseEntity.ok(ApiResponse.ok(service.getCustomerMonth(customerId, year, month))); }
}
