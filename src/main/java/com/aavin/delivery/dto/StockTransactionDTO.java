package com.aavin.delivery.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class StockTransactionDTO {
    private Long id; private Long packetTypeId; private String packetTypeName; private String packetColor;
    private String transactionType; private Integer quantity; private BigDecimal unitCost; private BigDecimal totalCost;
    private Long supplierId; private String supplierName; private String notes;
    private LocalDate transactionDate; private LocalDateTime createdAt;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Long getPacketTypeId() { return packetTypeId; } public void setPacketTypeId(Long packetTypeId) { this.packetTypeId = packetTypeId; }
    public String getPacketTypeName() { return packetTypeName; } public void setPacketTypeName(String packetTypeName) { this.packetTypeName = packetTypeName; }
    public String getPacketColor() { return packetColor; } public void setPacketColor(String packetColor) { this.packetColor = packetColor; }
    public String getTransactionType() { return transactionType; } public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    public Integer getQuantity() { return quantity; } public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitCost() { return unitCost; } public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public BigDecimal getTotalCost() { return totalCost; } public void setTotalCost(BigDecimal totalCost) { this.totalCost = totalCost; }
    public Long getSupplierId() { return supplierId; } public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public String getSupplierName() { return supplierName; } public void setSupplierName(String supplierName) { this.supplierName = supplierName; }
    public String getNotes() { return notes; } public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getTransactionDate() { return transactionDate; } public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
