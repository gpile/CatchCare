package it.catchcare.trap;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.catchcare.trap.model.Trap;
import it.catchcare.trap.repository.TrapRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@Slf4j
@Testcontainers
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@AutoConfigureMockMvc
@SpringBootTest
class TrapServiceApplicationTests {

    // Define a MongoDB container for testing
    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:8.0");

    // Dynamically set the MongoDB properties for the Spring context
    @DynamicPropertySource
    static void setProperties(org.springframework.test.context.DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl); // Use the replica set URL for better compatibility
    }

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TrapRepository trapRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        trapRepository.deleteAll();
    }

	@Test
	void contextLoads() {
	}

/*    @Test
    void testHealthCheck() throws Exception {
        mvc.perform(get("/actuator/health").with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.status").value("UP"));
    }*/

    @Test
    void shouldReturnAllTraps() throws Exception {
        trapRepository.save(Trap.builder().id("trap1").build());
        trapRepository.save(Trap.builder().id("trap2").build());

        mvc.perform(get("/api/traps").with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value("trap1"));
    }

    @Test
    void shouldReturnTrap_whenTrapExists() throws Exception {
        trapRepository.save(Trap.builder().id("trap1").build());

        mvc.perform(get("/api/traps/trap1").with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("trap1"));
    }

    @Test
    void shouldNotReturnTrap_ifNotExists() throws Exception {
        trapRepository.save(Trap.builder().id("trap1").build());

        mvc.perform(get("/api/traps/trap2").with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewTrap_ifNotExists() throws Exception {
        Trap newTrap = Trap.builder().id("newTrap").build();

        mvc.perform(post("/api/traps")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newTrap))
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("newTrap"));
    }

    @Test
    void shouldNotCreateTrap_ifAlreadyExists() throws Exception {
        Trap newTrap = Trap.builder().id("newTrap").build();
        trapRepository.save(newTrap); // Pre-save the trap to simulate conflict

        mvc.perform(post("/api/traps")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newTrap))
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldUpdateTrap_ifExists() throws Exception {
        // Save trap before update
        trapRepository.save(Trap.builder().id("trap1").name("beforeUpdate").battery(100).build());

        Trap updatedTrap = Trap.builder().id("trap1").name("afterUpdate").battery(80).build();

        mvc.perform(put("/api/traps/trap1")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedTrap))
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("afterUpdate"))
                .andExpect(jsonPath("$.battery").value(80));
    }

    @Test
    void shouldNotUpdateTrap_ifNotExists() throws Exception {
        Trap updatedTrap = Trap.builder().id("trap2").name("afterUpdate").battery(80).build();

        mvc.perform(put("/api/traps/trap2")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updatedTrap))
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteTrap_ifExists() throws Exception {
        trapRepository.save(Trap.builder().id("trap1").build());

        mvc.perform(delete("/api/traps/trap1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotDeleteTrap_ifNotExists() throws Exception {
        mvc.perform(delete("/api/traps/trap1")
                        .with(jwt().authorities(new SimpleGrantedAuthority("ROLE_USER"))))
                .andExpect(status().isNotFound());
    }







}
