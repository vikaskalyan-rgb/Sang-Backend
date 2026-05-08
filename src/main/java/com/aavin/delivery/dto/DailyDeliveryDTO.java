package com.aavin.delivery.dto;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class DailyDeliveryDTO {
    private Long id; private Long customerId; private String customerName;
    private String customerPhone; private String customerAddress;
    private Double latitude; private Double longitude; private Integer deliveryOrder;
    private Long packetTypeId; private String packetColor; private String colorHex;
    private LocalDate deliveryDate; private Boolean isDelivered; private Integer packetsDelivered;
    private String substituteName; private LocalDateTime deliveredAt; private String notes;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; } public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; } public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getCustomerPhone() { return customerPhone; } public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }
    public String getCustomerAddress() { return customerAddress; } public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }
    public Double getLatitude() { return latitude; } public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; } public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Integer getDeliveryOrder() { return deliveryOrder; } public void setDeliveryOrder(Integer deliveryOrder) { this.deliveryOrder = deliveryOrder; }
    public Long getPacketTypeId() { return packetTypeId; } public void setPacketTypeId(Long packetTypeId) { this.packetTypeId = packetTypeId; }
    public String getPacketColor() { return packetColor; } public void setPacketColor(String packetColor) { this.packetColor = packetColor; }
    public String getColorHex() { return colorHex; } public void setColorHex(String colorHex) { this.colorHex = colorHex; }
    public LocalDate getDeliveryDate() { return deliveryDate; } public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }
    public Boolean getIsDelivered() { return isDelivered; } public void setIsDelivered(Boolean isDelivered) { this.isDelivered = isDelivered; }
    public Integer getPacketsDelivered() { return packetsDelivered; } public void setPacketsDelivered(Integer packetsDelivered) { this.packetsDelivered = packetsDelivered; }
    public String getSubstituteName() { return substituteName; } public void setSubstituteName(String substituteName) { this.substituteName = substituteName; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; } public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
    public String getNotes() { return notes; } public void setNotes(String notes) { this.notes = notes; }
}
