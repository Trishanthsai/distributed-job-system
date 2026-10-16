package com.distributed.jobprocessing.controller;

import com.distributed.jobprocessing.service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobService jobService;

    @BeforeEach
    void setUp() {
        jobService.resetStore();
    }

    @Test
    @DisplayName("POST /api/jobs - should accept job and return 202 Accepted")
    void testCreateJob_Success() throws Exception {
        String requestBody = """
            {
              "type": "GENERATE_REPORT",
              "payload": {
                "reportId": 101
              }
            }
            """;

        mockMvc.perform(post("/api/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.jobId", is(1)))
            .andExpect(jsonPath("$.type", is("GENERATE_REPORT")))
            .andExpect(jsonPath("$.status", is("QUEUED")))
            .andExpect(jsonPath("$.message", is("Job accepted")));
    }

    @Test
    @DisplayName("GET /api/jobs/{id} - should return 200 OK with job details")
    void testGetJobById_Success() throws Exception {
        String requestBody = """
            {
              "type": "GENERATE_REPORT",
              "payload": {
                "reportId": 101
              }
            }
            """;

        mockMvc.perform(post("/api/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
            .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/jobs/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.jobId", is(1)))
            .andExpect(jsonPath("$.type", is("GENERATE_REPORT")))
            .andExpect(jsonPath("$.status", is("QUEUED")))
            .andExpect(jsonPath("$.payload.reportId", is(101)))
            .andExpect(jsonPath("$.createdAt", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/jobs/{id} - should return 404 Not Found when job does not exist")
    void testGetJobById_NotFound() throws Exception {
        mockMvc.perform(get("/api/jobs/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status", is(404)))
            .andExpect(jsonPath("$.error", is("Not Found")))
            .andExpect(jsonPath("$.message", is("Job not found with id: 999")))
            .andExpect(jsonPath("$.path", is("/api/jobs/999")))
            .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    @DisplayName("GET /api/jobs - should return 200 OK with list of submitted jobs")
    void testGetAllJobs_Success() throws Exception {
        String job1 = """
            {
              "type": "GENERATE_REPORT",
              "payload": { "reportId": 101 }
            }
            """;

        String job2 = """
            {
              "type": "SEND_EMAIL",
              "payload": { "recipient": "user@example.com" }
            }
            """;

        mockMvc.perform(post("/api/jobs").contentType(MediaType.APPLICATION_JSON).content(job1))
            .andExpect(status().isAccepted());

        mockMvc.perform(post("/api/jobs").contentType(MediaType.APPLICATION_JSON).content(job2))
            .andExpect(status().isAccepted());

        mockMvc.perform(get("/api/jobs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[0].jobId", is(1)))
            .andExpect(jsonPath("$[0].type", is("GENERATE_REPORT")))
            .andExpect(jsonPath("$[1].jobId", is(2)))
            .andExpect(jsonPath("$[1].type", is("SEND_EMAIL")));
    }

    @Test
    @DisplayName("POST /api/jobs - should return 400 Bad Request when type is blank")
    void testCreateJob_Validation_BlankType() throws Exception {
        String invalidRequest = """
            {
              "type": "   ",
              "payload": { "reportId": 101 }
            }
            """;

        mockMvc.perform(post("/api/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is(400)))
            .andExpect(jsonPath("$.error", is("Bad Request")))
            .andExpect(jsonPath("$.validationErrors.type", notNullValue()));
    }

    @Test
    @DisplayName("POST /api/jobs - should return 400 Bad Request when payload is missing")
    void testCreateJob_Validation_MissingPayload() throws Exception {
        String invalidRequest = """
            {
              "type": "GENERATE_REPORT"
            }
            """;

        mockMvc.perform(post("/api/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is(400)))
            .andExpect(jsonPath("$.error", is("Bad Request")))
            .andExpect(jsonPath("$.validationErrors.payload", notNullValue()));
    }

    @Test
    @DisplayName("POST /api/jobs - should return 400 Bad Request when request body is empty or malformed")
    void testCreateJob_MalformedJson() throws Exception {
        mockMvc.perform(post("/api/jobs")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ invalid json"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is(400)))
            .andExpect(jsonPath("$.error", is("Bad Request")))
            .andExpect(jsonPath("$.message", is("Malformed JSON request or missing request body")));
    }

    @Test
    @DisplayName("GET /api/jobs/{id} - should return 400 Bad Request when id is not numeric")
    void testGetJobById_InvalidTypeMismatch() throws Exception {
        mockMvc.perform(get("/api/jobs/abc"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is(400)))
            .andExpect(jsonPath("$.error", is("Bad Request")));
    }
}
