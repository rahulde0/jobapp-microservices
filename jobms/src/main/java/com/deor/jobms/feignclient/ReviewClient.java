package com.deor.jobms.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deor.jobms.job.external.Review;



@FeignClient(name="REVIEW-SERVICE", url= "${review-service.url}")
public interface ReviewClient {
	
	@GetMapping("/reviews")
	List<Review> getAllReviews(@RequestParam("companyId") Long companyId);
}
