package it.catchcare.trap.mapper;

import it.catchcare.trap.dto.TrapDTO;
import it.catchcare.trap.model.Trap;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") // tells MapStruct to use Spring for dependency injection
public interface TrapMapper {
    TrapDTO toDto(Trap trap);
    Trap toEntity(TrapDTO dto);
    List<TrapDTO> toDtoList(List<Trap> traps);
}
