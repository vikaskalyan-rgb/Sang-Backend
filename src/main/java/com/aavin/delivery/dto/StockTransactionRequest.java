package com.aavin.delivery.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
public class StockTransactionRequest {
    @NotNull private Long packetTypeId;
    @NotBlank private String transactionType;
    @NotNull @Min(1) private Integer quantity;
    private Long supplierId; private BigDecimal unitCost; private String notes;
    public Long getPacketTypeId() { return packetTypeId; } public void setPacketTypeId(Long packetTypeId) { this.packetTypeId = packetTypeId; }
    public String getTransactionType() { return transactionType; } public void setTransactionType(String transactionType) { this.transactionType = transactionType; }
    public Integer getQuantity() { return quantity; } public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Long getSupplierId() { return supplierId; } public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }
    public BigDecimal getUnitCost() { return unitCost; } public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public String getNotes() { return notes; } public void setNotes(String notes) { this.notes = notes; }
}
