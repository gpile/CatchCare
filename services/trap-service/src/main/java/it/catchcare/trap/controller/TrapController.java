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
    public List<TrapDTO> getAll() {
        return mapper.toDtoList(service.findAll());
    }

    @GetMapping("/{id}")
    public TrapDTO getById(@PathVariable(name = "id") String id) {
        return mapper.toDto(service.findById(id));
    }

    @PostMapping({"", "/"})
    public TrapDTO create(@RequestBody TrapDTO dto) {
        return mapper.toDto(service.create(mapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public TrapDTO update(@PathVariable(name = "id") String id, @RequestBody TrapDTO dto) {
        return mapper.toDto(service.update(id, mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") String id) {
        service.delete(id);
    }

}
