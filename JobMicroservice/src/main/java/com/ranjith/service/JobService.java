package com.ranjith.service;

import java.util.List;

import com.ranjith.dto.JobDTO;
import com.ranjith.entity.Job;

public interface JobService {

	public List<JobDTO> getAllJobs();
	public void createJob(Job job);
	public JobDTO getJobById(Long id);
	public boolean deleteJobByID(Long id);
	public boolean updateJobByID(Long id,Job updatedJob);
}
