package it.catchcare.trap.repository;

import it.catchcare.trap.model.Trap;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TrapRepository extends MongoRepository<Trap, String> {}
