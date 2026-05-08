package com.aavin.delivery.service;

import com.aavin.delivery.dto.SupplierDTO;
import com.aavin.delivery.entity.Supplier;
import com.aavin.delivery.exception.ResourceNotFoundException;
import com.aavin.delivery.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository repo;
    public SupplierService(SupplierRepository repo) { this.repo = repo; }

    public List<SupplierDTO> getAll() { return repo.findByIsActiveTrueOrderByNameAsc().stream().map(this::toDto).toList(); }

    @Transactional
    public SupplierDTO create(SupplierDTO req) {
        Supplier s = new Supplier();
        s.setName(req.getName()); s.setPhone(req.getPhone());
        s.setAddress(req.getAddress()); s.setNotes(req.getNotes()); s.setIsActive(true);
        return toDto(repo.save(s));
    }

    @Transactional
    public SupplierDTO update(Long id, SupplierDTO req) {
        Supplier s = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id));
        if (req.getName() != null)    s.setName(req.getName());
        if (req.getPhone() != null)   s.setPhone(req.getPhone());
        if (req.getAddress() != null) s.setAddress(req.getAddress());
        if (req.getNotes() != null)   s.setNotes(req.getNotes());
        return toDto(repo.save(s));
    }

    @Transactional
    public void delete(Long id) {
        Supplier s = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Supplier not found: " + id));
        s.setIsActive(false); repo.save(s);
    }

    public SupplierDTO toDto(Supplier s) {
        SupplierDTO d = new SupplierDTO();
        d.setId(s.getId()); d.setName(s.getName()); d.setPhone(s.getPhone());
        d.setAddress(s.getAddress()); d.setNotes(s.getNotes());
        d.setIsActive(s.getIsActive()); d.setCreatedAt(s.getCreatedAt());
        return d;
    }
}
