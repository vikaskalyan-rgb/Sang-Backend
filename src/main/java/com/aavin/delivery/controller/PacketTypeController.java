package com.aavin.delivery.controller;
import com.aavin.delivery.dto.*;
import com.aavin.delivery.service.PacketTypeService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/packet-types")
public class PacketTypeController {
    private final PacketTypeService service;
    public PacketTypeController(PacketTypeService service) { this.service = service; }

    @GetMapping public ResponseEntity<ApiResponse<List<PacketTypeDTO>>> getAll() { return ResponseEntity.ok(ApiResponse.ok(service.getAll())); }
    @PostMapping public ResponseEntity<ApiResponse<PacketTypeDTO>> create(@Valid @RequestBody PacketTypeDTO req) { return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Created", service.create(req))); }
    @PutMapping("/{id}") public ResponseEntity<ApiResponse<PacketTypeDTO>> update(@PathVariable Long id, @RequestBody PacketTypeDTO req) { return ResponseEntity.ok(ApiResponse.ok("Updated", service.update(id, req))); }
    @DeleteMapping("/{id}") public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.ok(ApiResponse.ok("Deleted", null)); }
}
