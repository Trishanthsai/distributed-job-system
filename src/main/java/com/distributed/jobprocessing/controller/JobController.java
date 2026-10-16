package com.distributed.jobprocessing.controller;

import com.distributed.jobprocessing.dto.CreateJobRequest;
import com.distributed.jobprocessing.dto.JobResponse;
import com.distributed.jobprocessing.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller exposing endpoints for job submission and status retrieval.
 */
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * Accepts a new job and returns HTTP 202 Accepted.
     *
     * @param request the validated job submission payload
     * @return 202 Accepted with JobResponse
     */
    @PostMapping
    public ResponseEntity<JobResponse> submitJob(@Valid @RequestBody CreateJobRequest request) {
        JobResponse response = jobService.createJob(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    /**
     * Retrieves a job by its unique identifier.
     *
     * @param id the job ID
     * @return 200 OK with JobResponse, or 404 Not Found if missing
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long id) {
        JobResponse response = jobService.getJobById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Lists all submitted jobs.
     *
     * @return 200 OK with the list of jobs
     */
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {
        List<JobResponse> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }
}
