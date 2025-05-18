package it.catchcare.trap.controller;

import it.catchcare.trap.dto.TrapDTO;
import it.catchcare.trap.mapper.TrapMapper;
import it.catchcare.trap.service.TrapService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/traps")
public class TrapController {

    private final TrapService service;
    private final TrapMapper mapper;

    public TrapController(TrapService service, TrapMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<TrapDTO>> getAll() {
        return ResponseEntity.ok(mapper.toDtoList(service.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrapDTO> getById(@PathVariable(name = "id") String id) {
        return service.findById(id)
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping({"", "/"})
    public ResponseEntity<TrapDTO> create(@RequestBody TrapDTO dto) {
        return ResponseEntity.ok(mapper.toDto(service.create(mapper.toEntity(dto))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrapDTO> update(@PathVariable(name = "id") String id, @RequestBody TrapDTO dto) {
        return service.update(id, mapper.toEntity(dto))
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}
