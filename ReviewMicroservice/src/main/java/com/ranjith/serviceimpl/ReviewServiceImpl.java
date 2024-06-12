package com.ranjith.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ranjith.entity.Review;
import com.ranjith.repository.ReviewRepository;
import com.ranjith.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	private ReviewRepository reviewRepository;
	
	public ReviewServiceImpl(ReviewRepository reviewRepository) {
		this.reviewRepository = reviewRepository;
	}
	
	@Override
	public List<Review> getAllReviews(Long companyId) {
		List<Review> reviews = reviewRepository.findByCompanyId(companyId);
		return reviews;
	}
	@Override
	public boolean addReview(Long companyId, Review review) {
		if(companyId!=null) {
			review.setCompanyId(companyId);
			reviewRepository.save(review);
			return true;
		}
		return false;
	}
	@Override
	public Review getReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElse(null);
		return review;
		
		/*
		 * List<Review> reviews = reviewRepository.findByCompanyId(companyId); return
		 * reviews.stream().filter(review ->
		 * review.getId().equals(reviewId)).findFirst().orElse(null);
		 */
	}
	@Override
	public boolean updateReview(Long reviewId, Review updateReview) {
		Review review = reviewRepository.findById(reviewId).orElse(null);
		if(review !=null && reviewRepository.existsById(reviewId)) {
			review.setTitle(updateReview.getTitle());
			review.setDescription(updateReview.getDescription());
			review.setCompanyId(updateReview.getCompanyId());
			review.setRating(updateReview.getRating());
			reviewRepository.save(review);
			return true;
		}
		return false;
	}
	@Override
	public boolean deleteReview(Long reviewId) {
		Review review = reviewRepository.findById(reviewId).orElse(null);
		if(review!=null) {
			reviewRepository.deleteById(reviewId);
			return true;
			}
		return false;
	}
	
	
	
}
