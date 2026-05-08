package com.aavin.delivery.dto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
public class DashboardDTO {
    private long totalCustomers; private long deliveredToday; private long totalCustomersActive;
    private long packetsDeliveredToday; private int pctToday;
    private BigDecimal monthIncome; private BigDecimal monthExpenses;
    private BigDecimal netProfit; private BigDecimal procurementCost;
    private int totalStock; private List<StockDTO> lowStockItems;
    private List<PendingPaymentDTO> pendingPayments; private List<BirthdayDTO> upcomingBirthdays;

    public long getTotalCustomers() { return totalCustomers; } public void setTotalCustomers(long totalCustomers) { this.totalCustomers = totalCustomers; }
    public long getDeliveredToday() { return deliveredToday; } public void setDeliveredToday(long deliveredToday) { this.deliveredToday = deliveredToday; }
    public long getTotalCustomersActive() { return totalCustomersActive; } public void setTotalCustomersActive(long totalCustomersActive) { this.totalCustomersActive = totalCustomersActive; }
    public long getPacketsDeliveredToday() { return packetsDeliveredToday; } public void setPacketsDeliveredToday(long packetsDeliveredToday) { this.packetsDeliveredToday = packetsDeliveredToday; }
    public int getPctToday() { return pctToday; } public void setPctToday(int pctToday) { this.pctToday = pctToday; }
    public BigDecimal getMonthIncome() { return monthIncome; } public void setMonthIncome(BigDecimal monthIncome) { this.monthIncome = monthIncome; }
    public BigDecimal getMonthExpenses() { return monthExpenses; } public void setMonthExpenses(BigDecimal monthExpenses) { this.monthExpenses = monthExpenses; }
    public BigDecimal getNetProfit() { return netProfit; } public void setNetProfit(BigDecimal netProfit) { this.netProfit = netProfit; }
    public BigDecimal getProcurementCost() { return procurementCost; } public void setProcurementCost(BigDecimal procurementCost) { this.procurementCost = procurementCost; }
    public int getTotalStock() { return totalStock; } public void setTotalStock(int totalStock) { this.totalStock = totalStock; }
    public List<StockDTO> getLowStockItems() { return lowStockItems; } public void setLowStockItems(List<StockDTO> lowStockItems) { this.lowStockItems = lowStockItems; }
    public List<PendingPaymentDTO> getPendingPayments() { return pendingPayments; } public void setPendingPayments(List<PendingPaymentDTO> pendingPayments) { this.pendingPayments = pendingPayments; }
    public List<BirthdayDTO> getUpcomingBirthdays() { return upcomingBirthdays; } public void setUpcomingBirthdays(List<BirthdayDTO> upcomingBirthdays) { this.upcomingBirthdays = upcomingBirthdays; }

    public static class PendingPaymentDTO {
        private Long customerId; private String customerName; private String phone; private BigDecimal estimatedDue;
        public Long getCustomerId() { return customerId; } public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public String getCustomerName() { return customerName; } public void setCustomerName(String customerName) { this.customerName = customerName; }
        public String getPhone() { return phone; } public void setPhone(String phone) { this.phone = phone; }
        public BigDecimal getEstimatedDue() { return estimatedDue; } public void setEstimatedDue(BigDecimal estimatedDue) { this.estimatedDue = estimatedDue; }
    }
    public static class BirthdayDTO {
        private Long customerId; private String customerName; private LocalDate birthday; private int daysUntil;
        public Long getCustomerId() { return customerId; } public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public String getCustomerName() { return customerName; } public void setCustomerName(String customerName) { this.customerName = customerName; }
        public LocalDate getBirthday() { return birthday; } public void setBirthday(LocalDate birthday) { this.birthday = birthday; }
        public int getDaysUntil() { return daysUntil; } public void setDaysUntil(int daysUntil) { this.daysUntil = daysUntil; }
    }
}
