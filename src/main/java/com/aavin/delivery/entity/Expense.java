package com.aavin.delivery.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false) private String category;
    @Column(columnDefinition = "TEXT") private String description;
    @Column(nullable = false, precision = 10, scale = 2) private BigDecimal amount;
    @Column(name = "expense_date", nullable = false) private LocalDate expenseDate;
    @CreationTimestamp @Column(name = "created_at", updatable = false) private LocalDateTime createdAt;

    public Expense() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDate getExpenseDate() { return expenseDate; }
    public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
