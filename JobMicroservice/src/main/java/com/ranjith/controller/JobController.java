package com.ranjith.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ranjith.dto.JobDTO;
import com.ranjith.entity.Job;
import com.ranjith.service.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController {

	private JobService jobService;

	public JobController(JobService jobService) {
		this.jobService = jobService;
	}

	@GetMapping
	public ResponseEntity<List<JobDTO>> getAllJobs() {
		List<JobDTO> findAll = jobService.getAllJobs();
		return new ResponseEntity<List<JobDTO>>(findAll, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
		JobDTO jobWithCompanyDTO = jobService.getJobById(id);
		if (jobWithCompanyDTO != null) {
			return new ResponseEntity<>(jobWithCompanyDTO, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<String> createJob(@RequestBody Job job) {
		jobService.createJob(job);
		return new ResponseEntity<String>("Job Added Successfully", HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteJobById(@PathVariable Long id) {
		boolean isDeleted = jobService.deleteJobByID(id);
		if (isDeleted) {
			return new ResponseEntity<String>("Job Deleted Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PutMapping("/{id}")
	public ResponseEntity<String> updateJobById(@PathVariable Long id, @RequestBody Job updatedJob) {
		boolean updated = jobService.updateJobByID(id, updatedJob);
		if (updated) {
			return new ResponseEntity<String>("Job Updated Successfully", HttpStatus.ACCEPTED);

		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
