package it.catchcare.trap.service;

import it.catchcare.trap.exception.TrapAlreadyExistsException;
import it.catchcare.trap.exception.TrapNotFoundException;
import it.catchcare.trap.model.Trap;
import it.catchcare.trap.repository.TrapRepository;
import it.catchcare.trap.util.TrapStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TrapService {

    private final TrapRepository repository;

    public TrapService(TrapRepository repository) {
        this.repository = repository;
    }

    // methods
    public List<Trap> findAll() { return repository.findAll(); }

    public Trap findById(String id) {
        return repository.findById(id).orElseThrow(TrapNotFoundException::new);
    }

    public Trap update(String id, Trap trap) {
        return repository.findById(id)
                .map(existingTrap -> {
                    // Update the existing trap with new values, only for allowed fields
                    existingTrap.setName(trap.getName());
                    existingTrap.setStatus(trap.getStatus());
                    existingTrap.setBattery(trap.getBattery());
                    existingTrap.setLocation(trap.getLocation());
                    existingTrap.setLastUpdated(trap.getLastUpdated());
                    return repository.save(existingTrap);
                })
                .orElseThrow(TrapNotFoundException::new);
    }

    public Trap create(Trap trap) {
        if (trap.getId() != null && repository.existsById(trap.getId()))
            throw new TrapAlreadyExistsException("A trap with ID " + trap.getId() + " already exists");

        return repository.save(trap);
    }

    // used by Kafka listener
    public Trap updateStatus(String id, TrapStatus newStatus, Instant updatedAt) {
        return repository.findById(id)
                .map(existingTrap -> {
                    existingTrap.setStatus(newStatus);
                    existingTrap.setLastUpdated(updatedAt);
                    return repository.save(existingTrap);
                })
                // Create a new trap if it doesn't exist
                .orElseGet(() -> repository.save(Trap.builder()
                        .id(id)
                        .name("Trap #" + id)
                        .status(newStatus)
                        .lastUpdated(updatedAt)
                        .build())
                );
    }

    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new TrapNotFoundException();
        }
        repository.deleteById(id);
    }

}

