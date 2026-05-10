package com.aavin.delivery.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock")
public class Stock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "packet_type_id", nullable = false, unique = true) private PacketType packetType;
    @Column(nullable = false) private Integer quantity = 0;
    @UpdateTimestamp @Column(name = "updated_at") private LocalDateTime updatedAt;

    public Stock() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PacketType getPacketType() { return packetType; }
    public void setPacketType(PacketType packetType) { this.packetType = packetType; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}