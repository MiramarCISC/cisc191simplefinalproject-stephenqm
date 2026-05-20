package edu.sdccd.cisc191.game;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:controller-test-db;DB_CLOSE_DELAY=-1",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
@ActiveProfiles("test")
class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void postMatchReturnsCreatedMatch() throws Exception {
        mockMvc.perform(post("/api/matches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"playerOneName\":\"Ada\",\"playerTwoName\":\"Grace\",\"ranked\":true}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerOneName", equalTo("Ada")))
                .andExpect(jsonPath("$.playerTwoName", equalTo("Grace")))
                .andExpect(jsonPath("$.ranked", equalTo(true)));
    }

    @Test
    void getMatchesReturnsJsonArray() throws Exception {
        mockMvc.perform(get("/api/matches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
