package com.ranjith.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ranjith.clients.CompanyClient;
import com.ranjith.clients.ReviewClient;
import com.ranjith.dto.JobDTO;
import com.ranjith.entity.Job;
import com.ranjith.external.Company;
import com.ranjith.external.Review;
import com.ranjith.mapper.JobMapper;
import com.ranjith.repository.JobRepository;
import com.ranjith.service.JobService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JobServiceImpl implements JobService {

	private JobRepository jobRepository;

	private CompanyClient companyClient;
	
	private ReviewClient reviewClient;
	
	public JobServiceImpl(JobRepository jobRepository,CompanyClient companyClient,ReviewClient reviewClient) {
		this.jobRepository = jobRepository;
		this.companyClient = companyClient;
		this.reviewClient = reviewClient;
	}

	@Autowired
	RestTemplate restTemplate;

	@Override
	//@CircuitBreaker(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
	@Retry(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
	//@RateLimiter(name="companyBreaker",fallbackMethod = "companyBreakerFallback")
	public List<JobDTO> getAllJobs() {
		List<Job> jobs = jobRepository.findAll();
		// List<JobDTO> jobWithCompanyDTOs = new ArrayList<>();
		return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	public List<String> companyBreakerFallback(Exception e){
		List<String> ls= new ArrayList<String>();
		ls.add("dummy");
		return ls;
	}
	
	private JobDTO convertToDto(Job job) {
		// RestTemplate restTemplate = new RestTemplate();
		/*
		 * JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
		 * jobWithCompanyDTO.setJob(job);
		 */
		// When we know response object we select resttemplate getforobject if doesn't
		// know use the url by exchange
		/*
		 * Company company =
		 * restTemplate.getForObject("http://COMPANY-SERVICE:4547/companies/" +
		 * job.getCompanyId(), Company.class);
		 */
		Company company = companyClient.getCompany(job.getCompanyId());
		
		List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
		/*
		 * ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
		 * "http://REVIEW-SERVICE:4548/reviews?companyId=" + job.getCompanyId(),
		 * HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() { });
		 * List<Review> reviews=reviewResponse.getBody();
		 */
		
		JobDTO jobDTO = JobMapper.mapToJobWithCompanyDto(job, company,reviews);
		//jobDTO.setCompany(company);
		return jobDTO;
	}

	@Override
	public void createJob(Job job) {
		jobRepository.save(job);
	}

	@Override
	public JobDTO getJobById(Long id) {
		Job job = jobRepository.findById(id).orElse(null);
		return convertToDto(job);
	}

	@Override
	public boolean deleteJobByID(Long id) {
		try {
			jobRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateJobByID(Long id, Job updatedJob) {
		Optional<Job> jobOptional = jobRepository.findById(id);
		if (jobOptional.isPresent()) {
			Job job = jobOptional.get();
			job.setTitle(updatedJob.getTitle());
			job.setDescription(updatedJob.getDescription());
			job.setMaxSalary(updatedJob.getMaxSalary());
			job.setMinSalary(updatedJob.getMinSalary());
			job.setLocation(updatedJob.getLocation());
			jobRepository.save(job);
			return true;
		}
		return false;
	}

}
