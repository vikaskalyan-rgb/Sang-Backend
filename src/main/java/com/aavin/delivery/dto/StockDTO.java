package com.aavin.delivery.dto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
public class StockDTO {
    private Long id; private Long packetTypeId; private String packetTypeName;
    private String packetColor; private String colorHex; private BigDecimal pricePerPacket;
    private Integer quantity; private Boolean isLow; private LocalDateTime updatedAt;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Long getPacketTypeId() { return packetTypeId; } public void setPacketTypeId(Long packetTypeId) { this.packetTypeId = packetTypeId; }
    public String getPacketTypeName() { return packetTypeName; } public void setPacketTypeName(String packetTypeName) { this.packetTypeName = packetTypeName; }
    public String getPacketColor() { return packetColor; } public void setPacketColor(String packetColor) { this.packetColor = packetColor; }
    public String getColorHex() { return colorHex; } public void setColorHex(String colorHex) { this.colorHex = colorHex; }
    public BigDecimal getPricePerPacket() { return pricePerPacket; } public void setPricePerPacket(BigDecimal pricePerPacket) { this.pricePerPacket = pricePerPacket; }
    public Integer getQuantity() { return quantity; } public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Boolean getIsLow() { return isLow; } public void setIsLow(Boolean isLow) { this.isLow = isLow; }
    public LocalDateTime getUpdatedAt() { return updatedAt; } public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
