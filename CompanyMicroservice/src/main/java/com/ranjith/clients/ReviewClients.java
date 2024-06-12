package com.ranjith.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("REVIEW-SERVICE")
public interface ReviewClients {

	@GetMapping("/reviews/averageRating")
	Double getAverageRatingForCompany(@RequestParam("companyId")Long companyId);
	
}
