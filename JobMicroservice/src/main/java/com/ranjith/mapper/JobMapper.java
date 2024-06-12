package com.ranjith.mapper;

import java.util.List;

import com.ranjith.dto.JobDTO;
import com.ranjith.entity.Job;
import com.ranjith.external.Company;
import com.ranjith.external.Review;

public class JobMapper {

	public static JobDTO mapToJobWithCompanyDto(
			Job job,
			Company company,
			List<Review> reviews) {
		JobDTO jobDTO=new JobDTO();
		jobDTO.setId(job.getId());
		jobDTO.setTitle(job.getTitle());
		jobDTO.setDescription(job.getDescription());
		jobDTO.setMaxSalary(job.getMaxSalary());
		jobDTO.setMinSalary(job.getMinSalary());
		jobDTO.setLocation(job.getLocation());
		jobDTO.setCompany(company);
		jobDTO.setReviews(reviews);
		
		
		return jobDTO;
	}
}
