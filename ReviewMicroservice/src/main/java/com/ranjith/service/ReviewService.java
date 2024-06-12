package com.ranjith.service;

import java.util.List;

import com.ranjith.entity.Review;

public interface ReviewService {

	List<Review> getAllReviews(Long companyId);
	boolean addReview(Long companyId,Review review);
	Review getReview(Long reviewId);
	boolean updateReview(Long reviewId,Review updateReview);
	boolean deleteReview(Long reviewId);	
}
