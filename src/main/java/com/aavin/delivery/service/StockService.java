package com.aavin.delivery.service;

import com.aavin.delivery.dto.StockDTO;
import com.aavin.delivery.dto.StockTransactionDTO;
import com.aavin.delivery.dto.StockTransactionRequest;
import com.aavin.delivery.entity.*;
import com.aavin.delivery.exception.BadRequestException;
import com.aavin.delivery.exception.ResourceNotFoundException;
import com.aavin.delivery.repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepo;
    private final StockTransactionRepository txRepo;
    private final PacketTypeRepository packetTypeRepo;
    private final SupplierRepository supplierRepo;

    public StockService(StockRepository stockRepo, StockTransactionRepository txRepo,
                        PacketTypeRepository packetTypeRepo, SupplierRepository supplierRepo) {
        this.stockRepo = stockRepo; this.txRepo = txRepo;
        this.packetTypeRepo = packetTypeRepo; this.supplierRepo = supplierRepo;
    }

    public List<StockDTO> getAllStock() { return stockRepo.findAll().stream().map(this::toDto).toList(); }

    public List<StockTransactionDTO> getTransactions(int limit) {
        return txRepo.findByOrderByCreatedAtDesc(PageRequest.of(0, limit)).stream().map(this::toTxDto).toList();
    }

    @Transactional
    public StockDTO recordTransaction(StockTransactionRequest req) {
        PacketType pt = packetTypeRepo.findById(req.getPacketTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Packet type not found"));
        StockTransaction.TransactionType type;
        try { type = StockTransaction.TransactionType.valueOf(req.getTransactionType()); }
        catch (Exception e) { throw new BadRequestException("Invalid transaction type: " + req.getTransactionType()); }

        Supplier supplier = null;
        if (req.getSupplierId() != null) supplier = supplierRepo.findById(req.getSupplierId()).orElse(null);

        boolean isDeduction = type == StockTransaction.TransactionType.DELIVERY_DEDUCT
                || type == StockTransaction.TransactionType.MANUAL_ADJUST_OUT;

        Stock stock = stockRepo.findByPacketTypeId(pt.getId())
                .orElseGet(() -> { Stock s = new Stock(); s.setPacketType(pt); s.setQuantity(0); return s; });

        stock.setQuantity(isDeduction ? Math.max(0, stock.getQuantity() - req.getQuantity())
                                       : stock.getQuantity() + req.getQuantity());
        stockRepo.save(stock);

        StockTransaction tx = new StockTransaction();
        tx.setPacketType(pt); tx.setTransactionType(type); tx.setQuantity(req.getQuantity());
        tx.setUnitCost(req.getUnitCost()); tx.setSupplier(supplier);
        tx.setNotes(req.getNotes()); tx.setTransactionDate(LocalDate.now());
        txRepo.save(tx);

        return toDto(stock);
    }

    @Transactional
    public void deductForDelivery(Long packetTypeId, int quantity, Long deliveryId) {
        stockRepo.findByPacketTypeId(packetTypeId).ifPresent(stock -> {
            stock.setQuantity(Math.max(0, stock.getQuantity() - quantity));
            stockRepo.save(stock);
            packetTypeRepo.findById(packetTypeId).ifPresent(pt -> {
                StockTransaction tx = new StockTransaction();
                tx.setPacketType(pt); tx.setTransactionType(StockTransaction.TransactionType.DELIVERY_DEDUCT);
                tx.setQuantity(quantity); tx.setReferenceId(deliveryId);
                tx.setNotes("Auto-deducted on delivery"); tx.setTransactionDate(LocalDate.now());
                txRepo.save(tx);
            });
        });
    }

    public StockDTO toDto(Stock s) {
        StockDTO d = new StockDTO();
        d.setId(s.getId()); d.setQuantity(s.getQuantity()); d.setUpdatedAt(s.getUpdatedAt());
        d.setIsLow(s.getQuantity() < 50);
        if (s.getPacketType() != null) {
            d.setPacketTypeId(s.getPacketType().getId()); d.setPacketTypeName(s.getPacketType().getName());
            d.setPacketColor(s.getPacketType().getColor()); d.setColorHex(s.getPacketType().getColorHex());
            d.setPricePerPacket(s.getPacketType().getPricePerPacket());
        }
        return d;
    }

    private StockTransactionDTO toTxDto(StockTransaction t) {
        StockTransactionDTO d = new StockTransactionDTO();
        d.setId(t.getId()); d.setTransactionType(t.getTransactionType().name());
        d.setQuantity(t.getQuantity()); d.setUnitCost(t.getUnitCost());
        d.setNotes(t.getNotes()); d.setTransactionDate(t.getTransactionDate()); d.setCreatedAt(t.getCreatedAt());
        if (t.getUnitCost() != null) d.setTotalCost(t.getUnitCost().multiply(BigDecimal.valueOf(t.getQuantity())));
        if (t.getPacketType() != null) {
            d.setPacketTypeId(t.getPacketType().getId()); d.setPacketTypeName(t.getPacketType().getName());
            d.setPacketColor(t.getPacketType().getColor());
        }
        if (t.getSupplier() != null) {
            d.setSupplierId(t.getSupplier().getId()); d.setSupplierName(t.getSupplier().getName());
        }
        return d;
    }
}
