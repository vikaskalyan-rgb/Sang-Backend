package com.aavin.delivery.dto;
import jakarta.validation.constraints.*;
public class PinChangeRequest {
    @NotBlank private String currentPin;
    @NotBlank @Size(min=4,max=4) private String newPin;
    public String getCurrentPin() { return currentPin; }
    public void setCurrentPin(String currentPin) { this.currentPin = currentPin; }
    public String getNewPin() { return newPin; }
    public void setNewPin(String newPin) { this.newPin = newPin; }
}
