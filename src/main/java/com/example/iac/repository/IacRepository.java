package com.example.iac.repository;

import com.example.iac.model.IacRecord;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class IacRepository {
    private final Map<String, IacRecord> store = new ConcurrentHashMap<>();

    public IacRecord save(IacRecord r) {
        store.put(r.getId(), r);
        return r;
    }

    public Optional<IacRecord> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<IacRecord> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(String id) {
        store.remove(id);
    }

    public boolean exists(String id) {
        return store.containsKey(id);
    }
}
