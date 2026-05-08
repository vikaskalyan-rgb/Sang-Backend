package com.aavin.delivery.service;

import com.aavin.delivery.dto.PaymentDTO;
import com.aavin.delivery.entity.Customer;
import com.aavin.delivery.entity.Payment;
import com.aavin.delivery.exception.ResourceNotFoundException;
import com.aavin.delivery.repository.CustomerRepository;
import com.aavin.delivery.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepo;
    private final CustomerRepository customerRepo;
    public PaymentService(PaymentRepository paymentRepo, CustomerRepository customerRepo) {
        this.paymentRepo = paymentRepo; this.customerRepo = customerRepo;
    }

    public List<PaymentDTO> getByCustomer(Long customerId) {
        return paymentRepo.findByCustomerIdOrderByPaymentDateDesc(customerId).stream().map(this::toDto).toList();
    }

    public List<Long> getPaidCustomerIds(int month, int year) {
        return paymentRepo.findPaidCustomerIds(month, year);
    }

    @Transactional
    public PaymentDTO create(PaymentDTO req) {
        Customer c = customerRepo.findById(req.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found: " + req.getCustomerId()));
        Payment p = new Payment();
        p.setCustomer(c); p.setAmount(req.getAmount());
        p.setPaymentDate(req.getPaymentDate() != null ? req.getPaymentDate() : LocalDate.now());
        p.setBillingMonth(req.getBillingMonth()); p.setBillingYear(req.getBillingYear());
        p.setPaymentMethod(req.getPaymentMethod() != null ? req.getPaymentMethod() : "CASH");
        p.setNotes(req.getNotes());
        return toDto(paymentRepo.save(p));
    }

    @Transactional
    public void delete(Long id) {
        if (!paymentRepo.existsById(id)) throw new ResourceNotFoundException("Payment not found: " + id);
        paymentRepo.deleteById(id);
    }

    public PaymentDTO toDto(Payment p) {
        PaymentDTO d = new PaymentDTO();
        d.setId(p.getId()); d.setCustomerId(p.getCustomer().getId());
        d.setCustomerName(p.getCustomer().getName()); d.setAmount(p.getAmount());
        d.setPaymentDate(p.getPaymentDate()); d.setBillingMonth(p.getBillingMonth());
        d.setBillingYear(p.getBillingYear()); d.setPaymentMethod(p.getPaymentMethod());
        d.setNotes(p.getNotes()); d.setCreatedAt(p.getCreatedAt());
        return d;
    }
}
