package com.aavin.delivery.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class PaymentDTO {
    private Long id; private Long customerId; private String customerName;
    @NotNull @DecimalMin("0.01") private BigDecimal amount;
    private LocalDate paymentDate;
    @NotNull @Min(1) @Max(12) private Integer billingMonth;
    @NotNull private Integer billingYear;
    private String paymentMethod; private String notes; private LocalDateTime createdAt;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Long getCustomerId() { return customerId; } public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; } public void setCustomerName(String customerName) { this.customerName = customerName; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDate getPaymentDate() { return paymentDate; } public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public Integer getBillingMonth() { return billingMonth; } public void setBillingMonth(Integer billingMonth) { this.billingMonth = billingMonth; }
    public Integer getBillingYear() { return billingYear; } public void setBillingYear(Integer billingYear) { this.billingYear = billingYear; }
    public String getPaymentMethod() { return paymentMethod; } public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getNotes() { return notes; } public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
