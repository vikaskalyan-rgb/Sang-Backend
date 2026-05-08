package com.aavin.delivery.service;

import com.aavin.delivery.dto.AppConfigDTO;
import com.aavin.delivery.entity.AppConfig;
import com.aavin.delivery.exception.UnauthorizedException;
import com.aavin.delivery.repository.AppConfigRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

@Service
public class AuthService {
    private final AppConfigRepository configRepo;
    public AuthService(AppConfigRepository configRepo) { this.configRepo = configRepo; }

    public void verifyPin(String pin) {
        AppConfig config = getOrCreate();
        if (!sha256(pin).equalsIgnoreCase(config.getPinHash()))
            throw new UnauthorizedException("Invalid PIN");
    }

    @Transactional
    public void changePin(String currentPin, String newPin) {
        verifyPin(currentPin);
        AppConfig config = getOrCreate();
        config.setPinHash(sha256(newPin));
        configRepo.save(config);
    }

    public AppConfigDTO getConfig() {
        AppConfig cfg = getOrCreate();
        AppConfigDTO dto = new AppConfigDTO();
        dto.setLanguage(cfg.getLanguage());
        dto.setVendorName(cfg.getVendorName());
        dto.setLowStockThreshold(cfg.getLowStockThreshold());
        return dto;
    }

    @Transactional
    public AppConfigDTO updateConfig(AppConfigDTO req) {
        AppConfig cfg = getOrCreate();
        if (req.getLanguage() != null)          cfg.setLanguage(req.getLanguage());
        if (req.getVendorName() != null)        cfg.setVendorName(req.getVendorName());
        if (req.getLowStockThreshold() != null) cfg.setLowStockThreshold(req.getLowStockThreshold());
        configRepo.save(cfg);
        return getConfig();
    }

    private AppConfig getOrCreate() {
        return configRepo.findAll().stream().findFirst().orElseGet(() -> {
            AppConfig cfg = new AppConfig();
            cfg.setPinHash("03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4");
            cfg.setLanguage("en");
            cfg.setVendorName("Sangaiya's Aavin");
            cfg.setLowStockThreshold(50);
            return configRepo.save(cfg);
        });
    }

    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (Exception e) { throw new RuntimeException("Hashing failed", e); }
    }
}
