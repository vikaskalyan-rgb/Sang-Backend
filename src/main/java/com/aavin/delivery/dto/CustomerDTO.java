package com.aavin.delivery.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class CustomerDTO {
    private Long id;
    @NotBlank private String name;
    @NotBlank @Pattern(regexp="\\d{10}") private String phone;
    private String address;
    private Double latitude;
    private Double longitude;
    @NotNull @Min(1) private Integer deliveryOrder;
    private Integer defaultPackets;
    private LocalDate birthday;
    private String notes;
    private Boolean isActive;
    private PacketConfigDTO packetConfig;
    private LocalDateTime createdAt;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; } public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; } public void setAddress(String address) { this.address = address; }
    public Double getLatitude() { return latitude; } public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; } public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Integer getDeliveryOrder() { return deliveryOrder; } public void setDeliveryOrder(Integer deliveryOrder) { this.deliveryOrder = deliveryOrder; }
    public Integer getDefaultPackets() { return defaultPackets; } public void setDefaultPackets(Integer defaultPackets) { this.defaultPackets = defaultPackets; }
    public LocalDate getBirthday() { return birthday; } public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public String getNotes() { return notes; } public void setNotes(String notes) { this.notes = notes; }
    public Boolean getIsActive() { return isActive; } public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public PacketConfigDTO getPacketConfig() { return packetConfig; } public void setPacketConfig(PacketConfigDTO packetConfig) { this.packetConfig = packetConfig; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
