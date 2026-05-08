package com.aavin.delivery.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_deliveries",
       uniqueConstraints = @UniqueConstraint(columnNames = {"customer_id", "delivery_date"}))
public class DailyDelivery {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customer_id", nullable = false) private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "packet_type_id") private PacketType packetType;
    @Column(name = "delivery_date", nullable = false) private LocalDate deliveryDate;
    @Column(name = "is_delivered") private Boolean isDelivered = false;
    @Column(name = "packets_delivered") private Integer packetsDelivered;
    @Column(name = "substitute_name") private String substituteName;
    @Column(name = "delivered_at") private LocalDateTime deliveredAt;
    @Column(columnDefinition = "TEXT") private String notes;
    @CreationTimestamp @Column(name = "created_at", updatable = false) private LocalDateTime createdAt;
    @UpdateTimestamp @Column(name = "updated_at") private LocalDateTime updatedAt;

    public DailyDelivery() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public PacketType getPacketType() { return packetType; }
    public void setPacketType(PacketType packetType) { this.packetType = packetType; }
    public LocalDate getDeliveryDate() { return deliveryDate; }
    public void setDeliveryDate(LocalDate deliveryDate) { this.deliveryDate = deliveryDate; }
    public Boolean getIsDelivered() { return isDelivered; }
    public void setIsDelivered(Boolean isDelivered) { this.isDelivered = isDelivered; }
    public Integer getPacketsDelivered() { return packetsDelivered; }
    public void setPacketsDelivered(Integer packetsDelivered) { this.packetsDelivered = packetsDelivered; }
    public String getSubstituteName() { return substituteName; }
    public void setSubstituteName(String substituteName) { this.substituteName = substituteName; }
    public LocalDateTime getDeliveredAt() { return deliveredAt; }
    public void setDeliveredAt(LocalDateTime deliveredAt) { this.deliveredAt = deliveredAt; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
