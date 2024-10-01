package com.deor.companyms.company;

import java.util.List;

import com.deor.companyms.company.dto.ReviewMessage;

public interface CompanyService {
	List<Company> getAllCompanies();
	boolean updateCompany(Company company, Long id);
	void createCompany(Company company);
	boolean deleteCompanyById(Long id);
	Company getCompanyById(Long id);
	void updateCompanyRating(ReviewMessage reviewMessage);
}
