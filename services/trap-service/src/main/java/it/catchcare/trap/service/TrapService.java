package it.catchcare.trap.service;

import it.catchcare.trap.exception.TrapAlreadyExistsException;
import it.catchcare.trap.model.Trap;
import it.catchcare.trap.repository.TrapRepository;
import org.springframework.stereotype.Service;

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
    public Optional<Trap> findById(String id) { return repository.findById(id); }

    public Optional<Trap> update(String id, Trap trap) {
        return repository.findById(id)
                .map(existingTrap -> {
                    // Update the existing trap with new values, only for  allowed fields
                    existingTrap.setStatus(trap.getStatus());
                    existingTrap.setLocation(trap.getLocation());
                    existingTrap.setLastUpdated(trap.getLastUpdated());
                    return repository.save(existingTrap);
                });
    }

    public Trap create(Trap trap) {
        if (trap.getId() != null && repository.existsById(trap.getId()))
            throw new TrapAlreadyExistsException("A trap with ID " + trap.getId() + " already exists");

        return repository.save(trap);
    }

    // used by Kafka listener
    public Trap saveOrUpdate(Trap trap) { return repository.save(trap); }

}

