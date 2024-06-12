package com.ranjith.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ranjith.entity.Company;

public interface CompanyRepository extends JpaRepository<Company,Long>{
	

}
