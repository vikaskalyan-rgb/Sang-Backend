package com.aavin.delivery.service;

import com.aavin.delivery.dto.DashboardDTO;
import com.aavin.delivery.dto.StockDTO;
import com.aavin.delivery.entity.Customer;
import com.aavin.delivery.entity.DailyDelivery;
import com.aavin.delivery.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {
    private final CustomerRepository customerRepo;
    private final DailyDeliveryRepository deliveryRepo;
    private final PaymentRepository paymentRepo;
    private final ExpenseRepository expenseRepo;
    private final StockTransactionRepository txRepo;
    private final StockService stockService;

    public DashboardService(CustomerRepository customerRepo, DailyDeliveryRepository deliveryRepo,
                            PaymentRepository paymentRepo, ExpenseRepository expenseRepo,
                            StockTransactionRepository txRepo, StockService stockService) {
        this.customerRepo = customerRepo; this.deliveryRepo = deliveryRepo;
        this.paymentRepo = paymentRepo; this.expenseRepo = expenseRepo;
        this.txRepo = txRepo; this.stockService = stockService;
    }

    public DashboardDTO getSummary(LocalDate from, LocalDate to) {
        LocalDate today      = LocalDate.now();
        LocalDate monthStart = today.withDayOfMonth(1);
        LocalDate rangeFrom  = from != null ? from : monthStart;
        LocalDate rangeTo    = to   != null ? to   : today;

        List<Customer> active = customerRepo.findByIsActiveTrueOrderByDeliveryOrderAsc();
        DashboardDTO d = new DashboardDTO();

        long deliveredToday = deliveryRepo.countDeliveredByDate(today);
        Long pktsToday = deliveryRepo.sumPacketsByDate(today);
        d.setTotalCustomersActive((long) active.size());
        d.setTotalCustomers(customerRepo.count());
        d.setDeliveredToday(deliveredToday);
        d.setPacketsDeliveredToday(pktsToday != null ? pktsToday : 0L);
        d.setPctToday(active.isEmpty() ? 0 : (int) Math.round((deliveredToday * 100.0) / active.size()));

        List<DailyDelivery> delivered = deliveryRepo.findDeliveredBetween(rangeFrom, rangeTo);
        BigDecimal income = delivered.stream()
                .filter(del -> del.getPacketType() != null && del.getPacketsDelivered() != null)
                .map(del -> del.getPacketType().getPricePerPacket().multiply(BigDecimal.valueOf(del.getPacketsDelivered())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        d.setMonthIncome(income);

        BigDecimal expenses = expenseRepo.sumBetween(rangeFrom, rangeTo);
        d.setMonthExpenses(expenses != null ? expenses : BigDecimal.ZERO);

        BigDecimal procurement = txRepo.sumPurchaseCostBetween(rangeFrom, rangeTo);
        d.setProcurementCost(procurement != null ? procurement : BigDecimal.ZERO);
        d.setNetProfit(income.subtract(d.getMonthExpenses()).subtract(d.getProcurementCost()));

        List<StockDTO> allStock = stockService.getAllStock();
        d.setTotalStock(allStock.stream().mapToInt(StockDTO::getQuantity).sum());
        d.setLowStockItems(allStock.stream().filter(StockDTO::getIsLow).toList());

        List<Long> paidIds = paymentRepo.findPaidCustomerIds(today.getMonthValue(), today.getYear());
        List<DashboardDTO.PendingPaymentDTO> pending = active.stream()
                .filter(c -> !paidIds.contains(c.getId()))
                .map(c -> {
                    DashboardDTO.PendingPaymentDTO p = new DashboardDTO.PendingPaymentDTO();
                    p.setCustomerId(c.getId()); p.setCustomerName(c.getName()); p.setPhone(c.getPhone());
                    BigDecimal est = BigDecimal.ZERO;
                    if (c.getDefaultPacketType() != null) {
                        long daysSoFar = ChronoUnit.DAYS.between(monthStart, today) + 1;
                        est = c.getDefaultPacketType().getPricePerPacket()
                                .multiply(BigDecimal.valueOf(c.getDefaultPackets()))
                                .multiply(BigDecimal.valueOf(daysSoFar));
                    }
                    p.setEstimatedDue(est);
                    return p;
                }).toList();
        d.setPendingPayments(pending);

        List<DashboardDTO.BirthdayDTO> birthdays = new ArrayList<>();
        for (Customer c : active) {
            if (c.getBirthday() == null) continue;
            LocalDate bday = c.getBirthday().withYear(today.getYear());
            if (bday.isBefore(today)) bday = bday.plusYears(1);
            long daysUntil = ChronoUnit.DAYS.between(today, bday);
            if (daysUntil <= 7) {
                DashboardDTO.BirthdayDTO bd = new DashboardDTO.BirthdayDTO();
                bd.setCustomerId(c.getId()); bd.setCustomerName(c.getName());
                bd.setBirthday(c.getBirthday()); bd.setDaysUntil((int) daysUntil);
                birthdays.add(bd);
            }
        }
        d.setUpcomingBirthdays(birthdays);
        return d;
    }
}
