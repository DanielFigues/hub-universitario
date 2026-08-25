package br.edu.hub.controller;

import br.edu.hub.entity.Activity;
import br.edu.hub.entity.ActivityCategory;
import br.edu.hub.entity.ActivityStatus;
import br.edu.hub.repository.ActivityRepository;
import br.edu.hub.repository.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    private Activity openActivity;

    @BeforeEach
    void setUp() {
        registrationRepository.deleteAll();
        activityRepository.deleteAll();

        openActivity = activityRepository.save(
                new Activity(
                        "Workshop de APIs",
                        "Uma atividade aberta para os testes.",
                        ActivityCategory.WORKSHOP,
                        ActivityStatus.OPEN,
                        2,
                        0,
                        "Equipe Hub",
                        "Lab 1",
                        LocalDateTime.now().plusDays(2)
                )
        );
    }

    @Test
    void shouldListRegistrations() throws Exception {
        mockMvc.perform(
                post("/api/activities/" + openActivity.getId() + "/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "studentName": "Maria Souza",
                                "studentEmail": "maria@email.com"
                            }
                        """)
        )
        .andExpect(status().isCreated());

        mockMvc.perform(
                get("/api/activities/" + openActivity.getId() + "/registrations")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].studentEmail").value("maria@email.com"));
    }

    @Test
    void shouldCancelRegistration() throws Exception {
        mockMvc.perform(
                post("/api/activities/" + openActivity.getId() + "/registrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "studentName": "Maria Souza",
                                "studentEmail": "maria@email.com"
                            }
                        """)
        ).andExpect(status().isCreated());

        mockMvc.perform(
                delete("/api/activities/" + openActivity.getId() + "/registrations")
                        .param("studentEmail", "maria@email.com")
        )
        .andExpect(status().isNoContent());
    }
}