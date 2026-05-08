package com.aavin.delivery.repository;

import com.aavin.delivery.entity.DailyDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyDeliveryRepository extends JpaRepository<DailyDelivery, Long> {

    List<DailyDelivery> findByDeliveryDateOrderByCustomerDeliveryOrderAsc(LocalDate date);

    List<DailyDelivery> findByCustomerIdAndDeliveryDateBetweenOrderByDeliveryDateAsc(
            Long customerId, LocalDate from, LocalDate to);

    Optional<DailyDelivery> findByCustomerIdAndDeliveryDate(Long customerId, LocalDate date);

    @Query("SELECT d FROM DailyDelivery d WHERE d.deliveryDate = :date AND d.isDelivered = true")
    List<DailyDelivery> findDeliveredByDate(LocalDate date);

    @Query("SELECT COUNT(d) FROM DailyDelivery d WHERE d.deliveryDate = :date AND d.isDelivered = true")
    long countDeliveredByDate(LocalDate date);

    @Query("SELECT SUM(d.packetsDelivered) FROM DailyDelivery d WHERE d.deliveryDate = :date AND d.isDelivered = true")
    Long sumPacketsByDate(LocalDate date);

    // For monthly income calculation
    @Query("SELECT d FROM DailyDelivery d JOIN FETCH d.customer JOIN FETCH d.packetType " +
           "WHERE d.deliveryDate BETWEEN :from AND :to AND d.isDelivered = true")
    List<DailyDelivery> findDeliveredBetween(LocalDate from, LocalDate to);
}
