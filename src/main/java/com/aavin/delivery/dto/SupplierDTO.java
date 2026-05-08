package com.aavin.delivery.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
public class SupplierDTO {
    private Long id;
    @NotBlank private String name;
    private String phone; private String address; private String notes;
    private Boolean isActive; private LocalDateTime createdAt;
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; } public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; } public void setAddress(String address) { this.address = address; }
    public String getNotes() { return notes; } public void setNotes(String notes) { this.notes = notes; }
    public Boolean getIsActive() { return isActive; } public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; } public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
