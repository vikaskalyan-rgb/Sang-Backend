package com.aavin.delivery.repository;

import com.aavin.delivery.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByExpenseDateBetweenOrderByExpenseDateDesc(LocalDate from, LocalDate to);

    List<Expense> findAllByOrderByExpenseDateDesc();

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.expenseDate BETWEEN :from AND :to")
    BigDecimal sumBetween(LocalDate from, LocalDate to);
}
