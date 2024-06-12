package com.ranjith.service;

import java.util.List;

import com.ranjith.dto.ReviewMessage;
import com.ranjith.entity.Company;

public interface CompanyService {

	List<Company> getAllCompanies();
	boolean updateCompany(Company company,Long id);
	void createCompany(Company company);
	boolean deleteComapanyById(Long id);
	Company getCompanyById(Long id);
	void updateCompanyRating(ReviewMessage reviewMessage);
}
