package com.aavin.delivery.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch = FetchType.EAGER) @JoinColumn(name = "customer_id", nullable = false) private Customer customer;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal amount;
    @Column(name = "payment_date", nullable = false) private LocalDate paymentDate;
    @Column(name = "billing_month", nullable = false) private Integer billingMonth;
    @Column(name = "billing_year", nullable = false) private Integer billingYear;
    @Column(name = "payment_method", length = 50) private String paymentMethod = "CASH";
    @Column(columnDefinition = "TEXT") private String notes;
    @CreationTimestamp @Column(name = "created_at", updatable = false) private LocalDateTime createdAt;

    public Payment() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public Integer getBillingMonth() { return billingMonth; }
    public void setBillingMonth(Integer billingMonth) { this.billingMonth = billingMonth; }
    public Integer getBillingYear() { return billingYear; }
    public void setBillingYear(Integer billingYear) { this.billingYear = billingYear; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}