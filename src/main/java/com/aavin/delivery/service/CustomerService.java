package com.aavin.delivery.service;

import com.aavin.delivery.dto.CustomerDTO;
import com.aavin.delivery.dto.PacketConfigDTO;
import com.aavin.delivery.entity.Customer;
import com.aavin.delivery.entity.PacketType;
import com.aavin.delivery.exception.ResourceNotFoundException;
import com.aavin.delivery.repository.CustomerRepository;
import com.aavin.delivery.repository.PacketTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final PacketTypeRepository packetTypeRepo;
    public CustomerService(CustomerRepository customerRepo, PacketTypeRepository packetTypeRepo) {
        this.customerRepo = customerRepo; this.packetTypeRepo = packetTypeRepo;
    }

    public List<CustomerDTO> getAll() {
        return customerRepo.findByIsActiveTrueOrderByDeliveryOrderAsc().stream().map(this::toDto).toList();
    }

    public CustomerDTO getById(Long id) { return toDto(findOrThrow(id)); }

    @Transactional
    public CustomerDTO create(CustomerDTO req) {
        int targetOrder = req.getDeliveryOrder();
        customerRepo.shiftOrdersUp(targetOrder, -1L);
        Customer c = new Customer();
        c.setName(req.getName()); c.setPhone(req.getPhone()); c.setAddress(req.getAddress());
        c.setLatitude(req.getLatitude()); c.setLongitude(req.getLongitude());
        c.setDeliveryOrder(targetOrder);
        c.setDefaultPackets(req.getDefaultPackets() != null ? req.getDefaultPackets() : 2);
        c.setBirthday(req.getBirthday()); c.setNotes(req.getNotes()); c.setIsActive(true);
        if (req.getPacketConfig() != null) {
            c.setPacketConfigType(req.getPacketConfig().getType());
            c.setPacketCount(req.getPacketConfig().getCount() != null ? req.getPacketConfig().getCount() : 2);
            if (req.getPacketConfig().getPacketTypeId() != null) {
                packetTypeRepo.findById(req.getPacketConfig().getPacketTypeId()).ifPresent(c::setDefaultPacketType);
            }
        }
        return toDto(customerRepo.save(c));
    }

    @Transactional
    public CustomerDTO update(Long id, CustomerDTO req) {
        Customer c = findOrThrow(id);
        int oldOrder = c.getDeliveryOrder();
        int newOrder = req.getDeliveryOrder() != null ? req.getDeliveryOrder() : oldOrder;
        if (newOrder != oldOrder) {
            if (newOrder < oldOrder) customerRepo.shiftOrdersUp(newOrder, id);
            else customerRepo.shiftOrdersDown(oldOrder, id);
            c.setDeliveryOrder(newOrder);
        }
        if (req.getName() != null)           c.setName(req.getName());
        if (req.getPhone() != null)          c.setPhone(req.getPhone());
        if (req.getAddress() != null)        c.setAddress(req.getAddress());
        if (req.getLatitude() != null)       c.setLatitude(req.getLatitude());
        if (req.getLongitude() != null)      c.setLongitude(req.getLongitude());
        if (req.getDefaultPackets() != null) c.setDefaultPackets(req.getDefaultPackets());
        if (req.getBirthday() != null)       c.setBirthday(req.getBirthday());
        if (req.getNotes() != null)          c.setNotes(req.getNotes());
        if (req.getPacketConfig() != null) {
            c.setPacketConfigType(req.getPacketConfig().getType());
            if (req.getPacketConfig().getCount() != null) c.setPacketCount(req.getPacketConfig().getCount());
            if (req.getPacketConfig().getPacketTypeId() != null) {
                packetTypeRepo.findById(req.getPacketConfig().getPacketTypeId()).ifPresent(c::setDefaultPacketType);
            }
        }
        return toDto(customerRepo.save(c));
    }

    @Transactional
    public void delete(Long id) {
        Customer c = findOrThrow(id);
        c.setIsActive(false); customerRepo.save(c);
        List<Customer> remaining = customerRepo.findByIsActiveTrueOrderByDeliveryOrderAsc();
        for (int i = 0; i < remaining.size(); i++) remaining.get(i).setDeliveryOrder(i + 1);
        customerRepo.saveAll(remaining);
    }

    private Customer findOrThrow(Long id) {
        return customerRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + id));
    }

    public CustomerDTO toDto(Customer c) {
        CustomerDTO d = new CustomerDTO();
        d.setId(c.getId()); d.setName(c.getName()); d.setPhone(c.getPhone()); d.setAddress(c.getAddress());
        d.setLatitude(c.getLatitude()); d.setLongitude(c.getLongitude());
        d.setDeliveryOrder(c.getDeliveryOrder()); d.setDefaultPackets(c.getDefaultPackets());
        d.setBirthday(c.getBirthday()); d.setNotes(c.getNotes());
        d.setIsActive(c.getIsActive()); d.setCreatedAt(c.getCreatedAt());
        if (c.getPacketConfigType() != null || c.getDefaultPacketType() != null) {
            PacketConfigDTO pc = new PacketConfigDTO();
            pc.setType(c.getPacketConfigType()); pc.setCount(c.getPacketCount());
            if (c.getDefaultPacketType() != null) pc.setPacketTypeId(c.getDefaultPacketType().getId());
            d.setPacketConfig(pc);
        }
        return d;
    }
}
