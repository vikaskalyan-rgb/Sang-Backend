package com.aavin.delivery.controller;
import com.aavin.delivery.dto.*;
import com.aavin.delivery.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/verify-pin")
    public ResponseEntity<ApiResponse<Boolean>> verifyPin(@Valid @RequestBody PinVerifyRequest req) {
        authService.verifyPin(req.getPin());
        return ResponseEntity.ok(ApiResponse.ok("PIN verified", true));
    }
    @PostMapping("/change-pin")
    public ResponseEntity<ApiResponse<Boolean>> changePin(@Valid @RequestBody PinChangeRequest req) {
        authService.changePin(req.getCurrentPin(), req.getNewPin());
        return ResponseEntity.ok(ApiResponse.ok("PIN changed", true));
    }
    @GetMapping("/config")
    public ResponseEntity<ApiResponse<AppConfigDTO>> getConfig() {
        return ResponseEntity.ok(ApiResponse.ok(authService.getConfig()));
    }
    @PutMapping("/config")
    public ResponseEntity<ApiResponse<AppConfigDTO>> updateConfig(@RequestBody AppConfigDTO req) {
        return ResponseEntity.ok(ApiResponse.ok("Updated", authService.updateConfig(req)));
    }
}
