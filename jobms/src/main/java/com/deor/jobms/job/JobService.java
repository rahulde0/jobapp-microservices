package com.deor.jobms.job;

import java.util.List;

import com.deor.jobms.job.dto.JobDTO;

public interface JobService {
	List<JobDTO> findAll();
	Job createJob(Job job);
	JobDTO getJobById(Long id);
	boolean deleteJobById(Long id);
	boolean updateJob(Long id, Job updatedJob);
}
