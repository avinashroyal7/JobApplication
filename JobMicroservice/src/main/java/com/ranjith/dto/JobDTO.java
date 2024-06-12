package com.ranjith.dto;

import java.util.List;

import com.ranjith.external.Company;
import com.ranjith.external.Review;

import lombok.Data;

@Data
public class JobDTO {

	private Long id;
	private String title;
	private String description;
	private String minSalary;
	private String maxSalary;
	private String location;
	private Company company;
	private List<Review> reviews;
}
