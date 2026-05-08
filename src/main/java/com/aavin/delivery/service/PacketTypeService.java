package com.aavin.delivery.service;

import com.aavin.delivery.dto.PacketTypeDTO;
import com.aavin.delivery.entity.PacketType;
import com.aavin.delivery.exception.ResourceNotFoundException;
import com.aavin.delivery.repository.PacketTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PacketTypeService {
    private final PacketTypeRepository repo;
    public PacketTypeService(PacketTypeRepository repo) { this.repo = repo; }

    public List<PacketTypeDTO> getAll() {
        return repo.findByIsActiveTrueOrderByIdAsc().stream().map(this::toDto).toList();
    }

    @Transactional
    public PacketTypeDTO create(PacketTypeDTO req) {
        PacketType pt = new PacketType();
        pt.setName(req.getName()); pt.setColor(req.getColor());
        pt.setColorHex(req.getColorHex()); pt.setPricePerPacket(req.getPricePerPacket());
        pt.setIsActive(true);
        return toDto(repo.save(pt));
    }

    @Transactional
    public PacketTypeDTO update(Long id, PacketTypeDTO req) {
        PacketType pt = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Packet type not found: " + id));
        if (req.getName() != null)           pt.setName(req.getName());
        if (req.getColor() != null)          pt.setColor(req.getColor());
        if (req.getColorHex() != null)       pt.setColorHex(req.getColorHex());
        if (req.getPricePerPacket() != null) pt.setPricePerPacket(req.getPricePerPacket());
        if (req.getIsActive() != null)       pt.setIsActive(req.getIsActive());
        return toDto(repo.save(pt));
    }

    @Transactional
    public void delete(Long id) {
        PacketType pt = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Packet type not found: " + id));
        pt.setIsActive(false);
        repo.save(pt);
    }

    public PacketTypeDTO toDto(PacketType p) {
        PacketTypeDTO d = new PacketTypeDTO();
        d.setId(p.getId()); d.setName(p.getName()); d.setColor(p.getColor());
        d.setColorHex(p.getColorHex()); d.setPricePerPacket(p.getPricePerPacket());
        d.setIsActive(p.getIsActive()); d.setCreatedAt(p.getCreatedAt()); d.setUpdatedAt(p.getUpdatedAt());
        return d;
    }
}
