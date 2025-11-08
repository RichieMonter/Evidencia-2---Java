package com.example.iac.controller;

import com.example.iac.dto.IacRequest;
import com.example.iac.model.IacRecord;
import com.example.iac.service.IacService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/iacs", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class IacController {
    private final IacService service;

    public IacController(IacService service) {
        this.service = service;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<IacRecord> create(@Valid @RequestBody IacRequest req, @RequestAttribute(required = false, name = "computedIac") Double computedIac) {
        IacRecord r = new IacRecord();
        r.setHeightMeters(req.getHeightMeters());
        r.setHipCm(req.getHipCm());
        // If filter computed IAC it is attached as request attribute; service will compute if null
        r.setIac(computedIac);
        IacRecord saved = service.create(r);
        return ResponseEntity.status(201).body(saved);
    }

    @GetMapping
    public List<IacRecord> list() { return service.list(); }

    @GetMapping("/{id}")
    public ResponseEntity<IacRecord> get(@PathVariable String id) {
        Optional<IacRecord> o = service.read(id);
        return o.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<IacRecord> update(@PathVariable String id, @Valid @RequestBody IacRequest req, @RequestAttribute(required = false, name = "computedIac") Double computedIac) {
        IacRecord r = new IacRecord();
        r.setHeightMeters(req.getHeightMeters());
        r.setHipCm(req.getHipCm());
        r.setIac(computedIac);
        Optional<IacRecord> updated = service.update(id, r);
        return updated.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean ok = service.delete(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
