package com.aavin.delivery.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_config")
public class AppConfig {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pin_hash", nullable = false) private String pinHash;
    @Column(nullable = false, length = 10) private String language = "en";
    @Column(name = "vendor_name") private String vendorName = "Sangaiya's Aavin";
    @Column(name = "low_stock_threshold") private Integer lowStockThreshold = 50;
    @UpdateTimestamp @Column(name = "updated_at") private LocalDateTime updatedAt;

    public AppConfig() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPinHash() { return pinHash; }
    public void setPinHash(String pinHash) { this.pinHash = pinHash; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    public Integer getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(Integer lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
