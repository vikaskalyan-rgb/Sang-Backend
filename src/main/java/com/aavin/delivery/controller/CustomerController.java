package com.aavin.delivery.controller;
import com.aavin.delivery.dto.*;
import com.aavin.delivery.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService service;
    public CustomerController(CustomerService service) { this.service = service; }

    @GetMapping public ResponseEntity<ApiResponse<List<CustomerDTO>>> getAll() { return ResponseEntity.ok(ApiResponse.ok(service.getAll())); }
    @GetMapping("/{id}") public ResponseEntity<ApiResponse<CustomerDTO>> getById(@PathVariable Long id) { return ResponseEntity.ok(ApiResponse.ok(service.getById(id))); }
    @PostMapping public ResponseEntity<ApiResponse<CustomerDTO>> create(@Valid @RequestBody CustomerDTO req) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Created", service.create(req))); }
    @PutMapping("/{id}") public ResponseEntity<ApiResponse<CustomerDTO>> update(@PathVariable Long id, @RequestBody CustomerDTO req) { return ResponseEntity.ok(ApiResponse.ok("Updated", service.update(id, req))); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok(ApiResponse.ok("Deleted", null)); }
}
