package com.aavin.delivery.repository;

import com.aavin.delivery.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByIsActiveTrueOrderByDeliveryOrderAsc();

    @Query("SELECT MAX(c.deliveryOrder) FROM Customer c WHERE c.isActive = true")
    Integer findMaxDeliveryOrder();

    // Shift delivery orders up to make room for inserted position
    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.deliveryOrder = c.deliveryOrder + 1 " +
           "WHERE c.deliveryOrder >= :fromOrder AND c.isActive = true AND c.id <> :excludeId")
    void shiftOrdersUp(int fromOrder, Long excludeId);

    // Shift delivery orders down after deletion/move
    @Modifying
    @Transactional
    @Query("UPDATE Customer c SET c.deliveryOrder = c.deliveryOrder - 1 " +
           "WHERE c.deliveryOrder > :fromOrder AND c.isActive = true AND c.id <> :excludeId")
    void shiftOrdersDown(int fromOrder, Long excludeId);
}
