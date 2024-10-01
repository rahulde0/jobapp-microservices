package com.deor.jobms.job.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.deor.jobms.feignclient.CompanyClient;
import com.deor.jobms.feignclient.ReviewClient;
import com.deor.jobms.job.Job;
import com.deor.jobms.job.JobRepository;
import com.deor.jobms.job.JobService;
import com.deor.jobms.job.dto.JobDTO;
import com.deor.jobms.job.external.Company;
import com.deor.jobms.job.external.Review;
import com.deor.jobms.mapper.JobMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JobServiceImpl implements JobService {

	private JobRepository jobRepository;

	//@Autowired
	//private RestTemplate restTemplate;
	
	@Autowired
	private CompanyClient companyClient;
	
	@Autowired
	private ReviewClient reviewClient;

	public JobServiceImpl(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}

	@Override
	@CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
	//@Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
	//@RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
	public List<JobDTO> findAll() {
		List<Job> jobs = jobRepository.findAll();
		return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	public List<String> companyBreakerFallback(Exception e){
		List<String> list=new ArrayList<>();
		list.add("Dummy");
		list.add(e.getMessage());
		return list;
	}

	private JobDTO convertToDTO(Job job) {

		//Company company = restTemplate.getForObject("http://company-service/companies/" + job.getCompanyId(),
		//		Company.class);
		Company company=companyClient.getCompanyById(job.getCompanyId());
		
		//ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
		//		"http://review-service/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null,
		//		new ParameterizedTypeReference<List<Review>>() {
		//		});
		
		//List<Review> reviews=reviewResponse.getBody();
		
		List<Review> reviews=reviewClient.getAllReviews(job.getCompanyId());
		JobDTO jobDTO = JobMapper.mapToJobDTO(job, company, reviews );
		return jobDTO;
	}

	@Override
	public Job createJob(Job job) {
		return jobRepository.save(job);
	}

	@Override
	public JobDTO getJobById(Long id) {
		Job job = jobRepository.findById(id).orElse(null);
		return convertToDTO(job);
	}

	@Override
	public boolean deleteJobById(Long id) {
		try {
			jobRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean updateJob(Long id, Job updatedJob) {
		Optional<Job> jobOptional = jobRepository.findById(id);
		if (jobOptional.isPresent()) {
			Job job = jobOptional.get();
			job.setTitle(updatedJob.getTitle());
			job.setDescription(updatedJob.getDescription());
			job.setMinSalary(updatedJob.getMinSalary());
			job.setMaxSalary(updatedJob.getMaxSalary());
			job.setLocation(updatedJob.getLocation());
			jobRepository.save(job);
			return true;
		}

		return false;
	}

}
