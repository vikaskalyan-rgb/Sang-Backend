package com.aavin.delivery.controller;
import com.aavin.delivery.dto.*;
import com.aavin.delivery.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService service;
    public ExpenseController(ExpenseService service) { this.service = service; }

    @GetMapping public ResponseEntity<ApiResponse<List<ExpenseDTO>>> getAll() { return ResponseEntity.ok(ApiResponse.ok(service.getAll())); }
    @PostMapping public ResponseEntity<ApiResponse<ExpenseDTO>> create(@Valid @RequestBody ExpenseDTO req) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Added", service.create(req))); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok(ApiResponse.ok("Deleted", null)); }
}
