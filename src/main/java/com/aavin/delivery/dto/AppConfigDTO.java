package com.aavin.delivery.dto;
public class AppConfigDTO {
    private String language;
    private String vendorName;
    private Integer lowStockThreshold;
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    public Integer getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(Integer lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }
}
