package it.catchcare.trap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrapDTO {
    private String id;
    private String location;
    private String status;
    private String lastUpdated;
}
