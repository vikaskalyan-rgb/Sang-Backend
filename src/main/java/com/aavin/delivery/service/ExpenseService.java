package com.aavin.delivery.service;

import com.aavin.delivery.dto.ExpenseDTO;
import com.aavin.delivery.entity.Expense;
import com.aavin.delivery.exception.ResourceNotFoundException;
import com.aavin.delivery.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository repo;
    public ExpenseService(ExpenseRepository repo) { this.repo = repo; }

    public List<ExpenseDTO> getAll() { return repo.findAllByOrderByExpenseDateDesc().stream().map(this::toDto).toList(); }

    @Transactional
    public ExpenseDTO create(ExpenseDTO req) {
        Expense e = new Expense();
        e.setCategory(req.getCategory()); e.setDescription(req.getDescription());
        e.setAmount(req.getAmount()); e.setExpenseDate(req.getExpenseDate());
        return toDto(repo.save(e));
    }

    @Transactional
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new ResourceNotFoundException("Expense not found: " + id);
        repo.deleteById(id);
    }

    public ExpenseDTO toDto(Expense e) {
        ExpenseDTO d = new ExpenseDTO();
        d.setId(e.getId()); d.setCategory(e.getCategory()); d.setDescription(e.getDescription());
        d.setAmount(e.getAmount()); d.setExpenseDate(e.getExpenseDate()); d.setCreatedAt(e.getCreatedAt());
        return d;
    }
}
