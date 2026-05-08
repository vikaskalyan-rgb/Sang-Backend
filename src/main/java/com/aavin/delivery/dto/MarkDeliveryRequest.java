package com.aavin.delivery.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public class MarkDeliveryRequest {
    @NotNull private Long customerId;
    @NotNull private LocalDate date;
    @NotNull @Min(1) private Integer packets;
    private Long packetTypeId;
    private String substituteName;
    public Long getCustomerId() { return customerId; } public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public LocalDate getDate() { return date; } public void setDate(LocalDate date) { this.date = date; }
    public Integer getPackets() { return packets; } public void setPackets(Integer packets) { this.packets = packets; }
    public Long getPacketTypeId() { return packetTypeId; } public void setPacketTypeId(Long packetTypeId) { this.packetTypeId = packetTypeId; }
    public String getSubstituteName() { return substituteName; } public void setSubstituteName(String substituteName) { this.substituteName = substituteName; }
}
