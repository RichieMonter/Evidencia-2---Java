package com.example.iac.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IacRecord {
    private String id;
    private Double heightMeters;
    private Double hipCm;
    private Double iac;

    public IacRecord() {}

    public IacRecord(String id, Double heightMeters, Double hipCm, Double iac) {
        this.id = id;
        this.heightMeters = heightMeters;
        this.hipCm = hipCm;
        this.iac = iac;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Double getHeightMeters() { return heightMeters; }
    public void setHeightMeters(Double heightMeters) { this.heightMeters = heightMeters; }

    public Double getHipCm() { return hipCm; }
    public void setHipCm(Double hipCm) { this.hipCm = hipCm; }

    public Double getIac() { return iac; }
    public void setIac(Double iac) { this.iac = iac; }
}
