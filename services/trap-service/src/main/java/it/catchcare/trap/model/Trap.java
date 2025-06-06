package it.catchcare.trap.model;

import it.catchcare.trap.util.TrapStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "traps")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor // Needed to resolve missing parameter name error: https://github.com/spring-projects/spring-data-mongodb/issues/4615
public class Trap {
    @Id
    private String id;
    private TrapStatus status;
    private String location;
    private Instant lastUpdated;
}
