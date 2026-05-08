package com.aavin.delivery.dto;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
public class ExpenseDTO {
    private Long id;
    @NotBlank private String category;
    private String description;
    @NotNull @DecimalMin("0.01") private BigDecimal amount;
    @NotNull private LocalDate expenseDate;
    private LocalDateTime createdAt;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getCategory() { return category; } public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDate getExpenseDate() { return expenseDate; } public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
