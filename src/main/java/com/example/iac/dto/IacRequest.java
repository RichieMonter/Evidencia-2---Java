package com.example.iac.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class IacRequest {
    @NotNull(message = "heightMeters is required (meters)")
    @Min(value = 0, message = "heightMeters must be positive")
    private Double heightMeters;

    @NotNull(message = "hipCm is required (centimeters)")
    @Min(value = 0, message = "hipCm must be positive")
    private Double hipCm;

    public Double getHeightMeters() { return heightMeters; }
    public void setHeightMeters(Double heightMeters) { this.heightMeters = heightMeters; }

    public Double getHipCm() { return hipCm; }
    public void setHipCm(Double hipCm) { this.hipCm = hipCm; }
}
