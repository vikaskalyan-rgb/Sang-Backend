package com.aavin.delivery.dto;
import jakarta.validation.constraints.*;
public class PinVerifyRequest {
    @NotBlank @Size(min=4,max=4) private String pin;
    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }
}
