package com.aavin.delivery.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String name;
    @Column(nullable = false, length = 20) private String phone;
    @Column(columnDefinition = "TEXT") private String address;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "delivery_order", nullable = false) private Integer deliveryOrder;
    @Column(name = "default_packets") private Integer defaultPackets = 2;
    private LocalDate birthday;
    @Column(columnDefinition = "TEXT") private String notes;
    @Column(name = "is_active") private Boolean isActive = true;
    @Column(name = "packet_config_type", length = 20) private String packetConfigType = "DAILY";
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "default_packet_type_id") private PacketType defaultPacketType;
    @Column(name = "packet_count") private Integer packetCount = 2;
    @CreationTimestamp @Column(name = "created_at", updatable = false) private LocalDateTime createdAt;
    @UpdateTimestamp @Column(name = "updated_at") private LocalDateTime updatedAt;

    public Customer() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public Integer getDeliveryOrder() { return deliveryOrder; }
    public void setDeliveryOrder(Integer deliveryOrder) { this.deliveryOrder = deliveryOrder; }
    public Integer getDefaultPackets() { return defaultPackets; }
    public void setDefaultPackets(Integer defaultPackets) { this.defaultPackets = defaultPackets; }
    public LocalDate getBirthday() { return birthday; }
    public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public String getPacketConfigType() { return packetConfigType; }
    public void setPacketConfigType(String packetConfigType) { this.packetConfigType = packetConfigType; }
    public PacketType getDefaultPacketType() { return defaultPacketType; }
    public void setDefaultPacketType(PacketType defaultPacketType) { this.defaultPacketType = defaultPacketType; }
    public Integer getPacketCount() { return packetCount; }
    public void setPacketCount(Integer packetCount) { this.packetCount = packetCount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
