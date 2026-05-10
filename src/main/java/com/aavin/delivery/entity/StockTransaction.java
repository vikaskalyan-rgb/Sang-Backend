package com.aavin.delivery.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_transactions")
public class StockTransaction {
    public enum TransactionType { PURCHASE, DELIVERY_DEDUCT, MANUAL_ADJUST_IN, MANUAL_ADJUST_OUT, RETURN }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "packet_type_id", nullable = false) private PacketType packetType;
    @Enumerated(EnumType.STRING) @Column(name = "transaction_type", nullable = false) private TransactionType transactionType;
    @Column(nullable = false) private Integer quantity;
    @Column(name = "unit_cost", precision = 10, scale = 2) private BigDecimal unitCost;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "supplier_id") private Supplier supplier;
    @Column(name = "reference_id") private Long referenceId;
    @Column(columnDefinition = "TEXT") private String notes;
    @Column(name = "transaction_date", nullable = false) private LocalDate transactionDate;
    @CreationTimestamp @Column(name = "created_at", updatable = false) private LocalDateTime createdAt;

    public StockTransaction() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public PacketType getPacketType() { return packetType; }
    public void setPacketType(PacketType packetType) { this.packetType = packetType; }
    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public BigDecimal getUnitCost() { return unitCost; }
    public void setUnitCost(BigDecimal unitCost) { this.unitCost = unitCost; }
    public Supplier getSupplier() { return supplier; }
    public void setSupplier(Supplier supplier) { this.supplier = supplier; }
    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}