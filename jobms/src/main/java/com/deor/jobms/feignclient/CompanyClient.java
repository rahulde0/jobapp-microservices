package com.deor.jobms.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.deor.jobms.job.external.Company;



@FeignClient(name="COMPANY-SERVICE", url= "${company-service.url}")
public interface CompanyClient {
	
	@GetMapping("/companies/{id}")
	Company getCompanyById(@PathVariable Long id);
}
