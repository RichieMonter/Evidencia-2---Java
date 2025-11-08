package com.example.iac.service;

import com.example.iac.model.IacRecord;
import com.example.iac.repository.IacRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class IacService {
    private final IacRepository repo;

    public IacService(IacRepository repo) {
        this.repo = repo;
    }

    // Saves a new record. If iac is null, calculate it.
    public IacRecord create(IacRecord r) {
        if (r.getId() == null) r.setId(UUID.randomUUID().toString());
        if (r.getIac() == null && r.getHeightMeters() != null && r.getHipCm() != null) {
            r.setIac(calculateIac(r.getHeightMeters(), r.getHipCm()));
        }
        return repo.save(r);
    }

    public Optional<IacRecord> read(String id) {
        return repo.findById(id);
    }

    public List<IacRecord> list() {
        return repo.findAll();
    }

    public Optional<IacRecord> update(String id, IacRecord r) {
        return repo.findById(id).map(existing -> {
            existing.setHeightMeters(r.getHeightMeters());
            existing.setHipCm(r.getHipCm());
            // recalculate if new values provided or provided iac is null
            existing.setIac(calculateIac(existing.getHeightMeters(), existing.getHipCm()));
            return repo.save(existing);
        });
    }

    public boolean delete(String id) {
        if (!repo.exists(id)) return false;
        repo.delete(id);
        return true;
    }

    // IAC calculation: BAI-like formula: (hip_cm / (height_m ^ 1.5)) - 18
    public static double calculateIac(double heightMeters, double hipCm) {
        if (heightMeters <= 0) throw new IllegalArgumentException("heightMeters must be > 0");
        return (hipCm / Math.pow(heightMeters, 1.5)) - 18.0;
    }
}
