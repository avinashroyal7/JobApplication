package com.ranjith.controller;

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

import com.ranjith.entity.Company;
import com.ranjith.service.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {

	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@GetMapping
	public ResponseEntity<List<Company>> getCompanies(){
		List<Company> list = companyService.getAllCompanies();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateCompany(@RequestBody Company company,@PathVariable Long id){
		companyService.updateCompany(company, id);
		return new ResponseEntity<String>("Company Updated Successfully", HttpStatus.ACCEPTED);
	}
	
	@PostMapping
	public ResponseEntity<String> addCompany(@RequestBody Company company){
		companyService.createCompany(company);
		return new ResponseEntity<String>("Company Added Successfully", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCompanyById(@PathVariable Long id){
		boolean isDeleted = companyService.deleteComapanyById(id);
		if(isDeleted) {
			return new ResponseEntity<String>("Company Deleted Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Company> getCompanyById(@PathVariable Long id){
		Company company = companyService.getCompanyById(id);
		if(company!=null) {
			return new ResponseEntity<Company>(company, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
