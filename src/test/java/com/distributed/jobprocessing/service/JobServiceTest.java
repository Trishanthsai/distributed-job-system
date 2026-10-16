package com.distributed.jobprocessing.service;

import com.distributed.jobprocessing.dto.CreateJobRequest;
import com.distributed.jobprocessing.dto.JobResponse;
import com.distributed.jobprocessing.exception.JobNotFoundException;
import com.distributed.jobprocessing.model.JobStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JobServiceTest {

    private JobService jobService;

    @BeforeEach
    void setUp() {
        jobService = new JobService();
    }

    @Test
    @DisplayName("createJob should create job with QUEUED status and generate sequential IDs")
    void testCreateJob_Success() {
        CreateJobRequest request1 = new CreateJobRequest("REPORT", Map.of("key", "val1"));
        CreateJobRequest request2 = new CreateJobRequest("SYNC", Map.of("key", "val2"));

        JobResponse response1 = jobService.createJob(request1);
        JobResponse response2 = jobService.createJob(request2);

        assertEquals(1L, response1.getJobId());
        assertEquals("REPORT", response1.getType());
        assertEquals(JobStatus.QUEUED, response1.getStatus());
        assertEquals("Job accepted", response1.getMessage());

        assertEquals(2L, response2.getJobId());
        assertEquals("SYNC", response2.getType());
        assertEquals(JobStatus.QUEUED, response2.getStatus());
        assertEquals("Job accepted", response2.getMessage());
    }

    @Test
    @DisplayName("getJobById should return job response when job exists")
    void testGetJobById_Found() {
        CreateJobRequest request = new CreateJobRequest("PROCESS", Map.of("itemId", 42));
        JobResponse created = jobService.createJob(request);

        JobResponse retrieved = jobService.getJobById(created.getJobId());

        assertNotNull(retrieved);
        assertEquals(created.getJobId(), retrieved.getJobId());
        assertEquals("PROCESS", retrieved.getType());
        assertEquals(JobStatus.QUEUED, retrieved.getStatus());
        assertEquals(Map.of("itemId", 42), retrieved.getPayload());
        assertNotNull(retrieved.getCreatedAt());
    }

    @Test
    @DisplayName("getJobById should throw JobNotFoundException when job does not exist")
    void testGetJobById_NotFound() {
        assertThrows(JobNotFoundException.class, () -> jobService.getJobById(999L));
    }

    @Test
    @DisplayName("getAllJobs should return all jobs sorted by ID")
    void testGetAllJobs_Success() {
        jobService.createJob(new CreateJobRequest("TYPE_A", Map.of("n", 1)));
        jobService.createJob(new CreateJobRequest("TYPE_B", Map.of("n", 2)));
        jobService.createJob(new CreateJobRequest("TYPE_C", Map.of("n", 3)));

        List<JobResponse> jobs = jobService.getAllJobs();

        assertEquals(3, jobs.size());
        assertEquals(1L, jobs.get(0).getJobId());
        assertEquals(2L, jobs.get(1).getJobId());
        assertEquals(3L, jobs.get(2).getJobId());
    }
}
