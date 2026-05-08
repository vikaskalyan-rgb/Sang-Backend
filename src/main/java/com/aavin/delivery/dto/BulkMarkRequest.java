package com.aavin.delivery.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public class BulkMarkRequest {
    @NotNull private LocalDate date;
    private String substituteName;
    public LocalDate getDate() { return date; } public void setDate(LocalDate date) { this.date = date; }
    public String getSubstituteName() { return substituteName; } public void setSubstituteName(String substituteName) { this.substituteName = substituteName; }
}
