package com.deor.jobms.mapper;

import java.util.List;

import com.deor.jobms.job.Job;
import com.deor.jobms.job.dto.JobDTO;
import com.deor.jobms.job.external.Company;
import com.deor.jobms.job.external.Review;

public class JobMapper {
	
	public static JobDTO mapToJobDTO(Job job, Company company, List<Review> reviews) {
		JobDTO jobDTO=new JobDTO();
		jobDTO.setId(job.getId());
		jobDTO.setDescription(job.getDescription());
		jobDTO.setCompany(company);
		jobDTO.setLocation(job.getLocation());
		jobDTO.setMaxSalary(job.getMaxSalary());
		jobDTO.setMinSalary(job.getMinSalary());
		jobDTO.setTitle(job.getTitle());
		jobDTO.setReviews(reviews);
		return jobDTO;
	}

}
