package com.aavin.delivery.repository;

import com.aavin.delivery.entity.StockTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

    List<StockTransaction> findByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT SUM(st.quantity * st.unitCost) FROM StockTransaction st " +
           "WHERE st.transactionType = 'PURCHASE' AND st.transactionDate BETWEEN :from AND :to " +
           "AND st.unitCost IS NOT NULL")
    java.math.BigDecimal sumPurchaseCostBetween(LocalDate from, LocalDate to);
}
