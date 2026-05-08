package com.aavin.delivery.service;

import com.aavin.delivery.dto.DailyDeliveryDTO;
import com.aavin.delivery.dto.MarkDeliveryRequest;
import com.aavin.delivery.entity.*;
import com.aavin.delivery.exception.ResourceNotFoundException;
import com.aavin.delivery.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {
    private final DailyDeliveryRepository deliveryRepo;
    private final CustomerRepository customerRepo;
    private final PacketTypeRepository packetTypeRepo;
    private final StockService stockService;

    public DeliveryService(DailyDeliveryRepository deliveryRepo, CustomerRepository customerRepo,
                           PacketTypeRepository packetTypeRepo, StockService stockService) {
        this.deliveryRepo = deliveryRepo; this.customerRepo = customerRepo;
        this.packetTypeRepo = packetTypeRepo; this.stockService = stockService;
    }

    public List<DailyDeliveryDTO> getToday() {
        LocalDate today = LocalDate.now();
        return customerRepo.findByIsActiveTrueOrderByDeliveryOrderAsc().stream()
                .map(c -> buildDto(c, deliveryRepo.findByCustomerIdAndDeliveryDate(c.getId(), today).orElse(null), today))
                .toList();
    }

    @Transactional
    public DailyDeliveryDTO markDelivered(MarkDeliveryRequest req) {
        Customer c = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + req.getCustomerId()));
        PacketType pt = req.getPacketTypeId() != null
                ? packetTypeRepo.findById(req.getPacketTypeId()).orElse(c.getDefaultPacketType())
                : c.getDefaultPacketType();
        DailyDelivery del = deliveryRepo.findByCustomerIdAndDeliveryDate(c.getId(), req.getDate())
                .orElseGet(() -> { DailyDelivery d = new DailyDelivery(); d.setCustomer(c); d.setDeliveryDate(req.getDate()); return d; });
        boolean wasDelivered = Boolean.TRUE.equals(del.getIsDelivered());
        int oldPackets = del.getPacketsDelivered() != null ? del.getPacketsDelivered() : 0;
        del.setIsDelivered(true); del.setPacketsDelivered(req.getPackets());
        del.setPacketType(pt); del.setSubstituteName(req.getSubstituteName());
        del.setDeliveredAt(LocalDateTime.now());
        DailyDelivery saved = deliveryRepo.save(del);
        if (pt != null) {
            int delta = req.getPackets() - (wasDelivered ? oldPackets : 0);
            if (delta > 0) stockService.deductForDelivery(pt.getId(), delta, saved.getId());
        }
        return buildDto(c, saved, req.getDate());
    }

    @Transactional
    public DailyDeliveryDTO unmark(Long customerId, LocalDate date) {
        Customer c = customerRepo.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));
        DailyDelivery del = deliveryRepo.findByCustomerIdAndDeliveryDate(customerId, date)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));
        del.setIsDelivered(false); del.setDeliveredAt(null);
        return buildDto(c, deliveryRepo.save(del), date);
    }

    @Transactional
    public DailyDeliveryDTO updatePackets(Long customerId, LocalDate date, Integer packets) {
        Customer c = customerRepo.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + customerId));
        DailyDelivery del = deliveryRepo.findByCustomerIdAndDeliveryDate(customerId, date)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found"));
        del.setPacketsDelivered(packets);
        return buildDto(c, deliveryRepo.save(del), date);
    }

    @Transactional
    public List<DailyDeliveryDTO> bulkMarkAll(LocalDate date, String substituteName) {
        for (Customer c : customerRepo.findByIsActiveTrueOrderByDeliveryOrderAsc()) {
            DailyDelivery del = deliveryRepo.findByCustomerIdAndDeliveryDate(c.getId(), date)
                    .orElseGet(() -> { DailyDelivery d = new DailyDelivery(); d.setCustomer(c); d.setDeliveryDate(date); return d; });
            if (!Boolean.TRUE.equals(del.getIsDelivered())) {
                del.setIsDelivered(true); del.setPacketsDelivered(c.getDefaultPackets());
                del.setPacketType(c.getDefaultPacketType()); del.setSubstituteName(substituteName);
                del.setDeliveredAt(LocalDateTime.now());
                DailyDelivery saved = deliveryRepo.save(del);
                if (c.getDefaultPacketType() != null)
                    stockService.deductForDelivery(c.getDefaultPacketType().getId(), c.getDefaultPackets(), saved.getId());
            }
        }
        return getToday();
    }

    public List<DailyDeliveryDTO> getCustomerMonth(Long customerId, int year, int month) {
        Customer c = customerRepo.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to   = from.withDayOfMonth(from.lengthOfMonth());
        return deliveryRepo.findByCustomerIdAndDeliveryDateBetweenOrderByDeliveryDateAsc(customerId, from, to)
                .stream().map(d -> buildDto(c, d, d.getDeliveryDate())).toList();
    }

    private DailyDeliveryDTO buildDto(Customer c, DailyDelivery del, LocalDate date) {
        DailyDeliveryDTO d = new DailyDeliveryDTO();
        d.setCustomerId(c.getId()); d.setCustomerName(c.getName()); d.setCustomerPhone(c.getPhone());
        d.setCustomerAddress(c.getAddress()); d.setLatitude(c.getLatitude()); d.setLongitude(c.getLongitude());
        d.setDeliveryOrder(c.getDeliveryOrder()); d.setDeliveryDate(date);
        PacketType pt = (del != null && del.getPacketType() != null) ? del.getPacketType() : c.getDefaultPacketType();
        if (pt != null) { d.setPacketTypeId(pt.getId()); d.setPacketColor(pt.getColor()); d.setColorHex(pt.getColorHex()); }
        if (del != null) {
            d.setId(del.getId()); d.setIsDelivered(del.getIsDelivered());
            d.setPacketsDelivered(del.getPacketsDelivered() != null ? del.getPacketsDelivered() : c.getDefaultPackets());
            d.setSubstituteName(del.getSubstituteName()); d.setDeliveredAt(del.getDeliveredAt()); d.setNotes(del.getNotes());
        } else {
            d.setIsDelivered(false); d.setPacketsDelivered(c.getDefaultPackets());
        }
        return d;
    }
}
