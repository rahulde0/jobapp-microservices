package com.deor.jobms.job;

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

import com.deor.jobms.job.dto.JobDTO;

@RestController
@RequestMapping("/jobs")
public class JobController {
	
	private JobService jobService;
	
	public JobController(JobService jobService) {
		this.jobService=jobService;
	}
	
	@GetMapping
	public ResponseEntity<List<JobDTO>> findAll(){
		return ResponseEntity.ok(jobService.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
		JobDTO jobDTO = jobService.getJobById(id);
		if(jobDTO != null)
			return new ResponseEntity<>(jobDTO,HttpStatus.OK);
		return new ResponseEntity<JobDTO>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<String> createJob(@RequestBody Job job) {
		jobService.createJob(job);
		return new ResponseEntity<String>("Job added successfully",HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteJob(@PathVariable Long id){
		boolean deleted = jobService.deleteJobById(id);
		if(deleted)
			return new ResponseEntity<>("Job deleted successfully", HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job updatedJob){
		boolean updated= jobService.updateJob(id, updatedJob);
		if(updated)
			return new ResponseEntity<>("Job updated successfully", HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
}
