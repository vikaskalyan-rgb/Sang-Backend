package com.aavin.delivery.repository;

import com.aavin.delivery.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCustomerIdOrderByPaymentDateDesc(Long customerId);

    Optional<Payment> findByCustomerIdAndBillingMonthAndBillingYear(
            Long customerId, int month, int year);

    @Query("SELECT p.customer.id FROM Payment p WHERE p.billingMonth = :month AND p.billingYear = :year")
    List<Long> findPaidCustomerIds(int month, int year);

    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.paymentDate BETWEEN :from AND :to")
    java.math.BigDecimal sumPaymentsBetween(LocalDate from, LocalDate to);
}
