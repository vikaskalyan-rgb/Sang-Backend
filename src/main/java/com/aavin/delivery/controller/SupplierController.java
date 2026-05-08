package com.aavin.delivery.controller;
import com.aavin.delivery.dto.*;
import com.aavin.delivery.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/suppliers")
public class SupplierController {
    private final SupplierService service;
    public SupplierController(SupplierService service) { this.service = service; }

    @GetMapping public ResponseEntity<ApiResponse<List<SupplierDTO>>> getAll() { return ResponseEntity.ok(ApiResponse.ok(service.getAll())); }
    @PostMapping public ResponseEntity<ApiResponse<SupplierDTO>> create(@Valid @RequestBody SupplierDTO req) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Created", service.create(req))); }
    @PutMapping("/{id}") public ResponseEntity<ApiResponse<SupplierDTO>> update(@PathVariable Long id, @RequestBody SupplierDTO req) { return ResponseEntity.ok(ApiResponse.ok("Updated", service.update(id, req))); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok(ApiResponse.ok("Deleted", null)); }
}
