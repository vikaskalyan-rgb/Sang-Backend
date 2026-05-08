package com.aavin.delivery.repository;

import com.aavin.delivery.entity.PacketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacketTypeRepository extends JpaRepository<PacketType, Long> {
    List<PacketType> findByIsActiveTrueOrderByIdAsc();
}
