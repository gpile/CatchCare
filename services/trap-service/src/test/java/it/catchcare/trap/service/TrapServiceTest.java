package it.catchcare.trap.service;

import it.catchcare.trap.exception.TrapAlreadyExistsException;
import it.catchcare.trap.exception.TrapNotFoundException;
import it.catchcare.trap.model.Trap;
import it.catchcare.trap.repository.TrapRepository;
import it.catchcare.trap.util.TrapStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrapServiceTest {

    @Mock
    TrapRepository repository;

    @InjectMocks
    TrapService service;

    @Test
    void findAllReturnsAllTraps() {
        List<Trap> traps = List.of(
                Trap.builder().id("id1").build(),
                Trap.builder().id("id2").build());
        when(repository.findAll()).thenReturn(traps);

        List<Trap> result = service.findAll();

        assertEquals(traps, result);
    }

    @Test
    void findByIdReturnsTrapIfExists() {
        Trap trap = Trap.builder().id("id1").build();
        when(repository.findById("id1")).thenReturn(Optional.of(trap));

        Trap result = service.findById("id1");

        assertEquals(trap, result);
    }

    @Test
    void findByIdThrowsExceptionIfNotExists() {
        when(repository.findById("id2")).thenReturn(Optional.empty());

        assertThrows(TrapNotFoundException.class, () -> service.findById("id2"));
    }

    @Test
    void updateUpdatesExistingTrapFields() {
        Trap existing = Trap.builder().id("id3").name("old").status(TrapStatus.ARMED).build();
        Trap update = Trap.builder().id("id3").name("new").status(TrapStatus.CLOSED).location("loc").lastUpdated(Instant.now()).build();
        when(repository.findById("id3")).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0)); // Return the saved entity

        Trap result = service.update("id3", update);

        assertEquals(update.getName(), result.getName());
        assertEquals(update.getStatus(), result.getStatus());
        assertEquals(update.getLocation(), result.getLocation());
        assertEquals(update.getLastUpdated(), result.getLastUpdated());
    }

    @Test
    void updateThrowsIfTrapNotFound() {
        when(repository.findById("id4")).thenReturn(Optional.empty());
        assertThrows(TrapNotFoundException.class, () -> service.update("id4", new Trap()));
    }

    @Test
    void createThrowsIfTrapWithIdAlreadyExists() {
        Trap trap = Trap.builder().id("id5").build();
        when(repository.existsById("id5")).thenReturn(true);

        assertThrows(TrapAlreadyExistsException.class, () -> service.create(trap));
    }

    @Test
    void createSavesTrapIfIdIsNull() {
        Trap trap = new Trap();
        when(repository.save(trap)).thenReturn(trap);

        Trap result = service.create(trap);

        assertEquals(trap, result);
    }

    @Test
    void createSavesTrapIfIdNotExists() {
        Trap trap = Trap.builder().id("id6").build();
        when(repository.existsById("id6")).thenReturn(false);
        when(repository.save(trap)).thenReturn(trap);

        Trap result = service.create(trap);

        assertEquals(trap, result);
    }

    @Test
    void updateStatusUpdatesExistingTrap() {
        Trap existing = Trap.builder().id("id7").status(TrapStatus.ARMED).lastUpdated(Instant.now()).build();
        when(repository.findById("id7")).thenReturn(Optional.of(existing));
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Instant now = Instant.now();

        Trap result = service.updateStatus("id7", TrapStatus.CLOSED, now);

        assertEquals(TrapStatus.CLOSED, result.getStatus());
        assertEquals(now, result.getLastUpdated());
    }

    @Test
    void updateStatusCreatesTrapIfNotExists() {
        when(repository.findById("id8")).thenReturn(Optional.empty());
        ArgumentCaptor<Trap> captor = ArgumentCaptor.forClass(Trap.class);
        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        Instant now = Instant.now();

        Trap result = service.updateStatus("id8", TrapStatus.ARMED, now);

        assertEquals("id8", result.getId());
        assertEquals(TrapStatus.ARMED, result.getStatus());
        assertEquals(now, result.getLastUpdated());
        assertEquals("Trap #id8", result.getName());
    }
}
