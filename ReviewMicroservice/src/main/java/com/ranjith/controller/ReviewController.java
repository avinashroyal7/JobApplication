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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ranjith.entity.Review;
import com.ranjith.messaging.ReviewMessageProducer;
import com.ranjith.service.ReviewService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
	
	private ReviewService reviewService;
	private ReviewMessageProducer reviewMessageProducer;
	
	public ReviewController(ReviewService reviewService,ReviewMessageProducer reviewMessageProducer) {
		this.reviewService = reviewService;
		this.reviewMessageProducer = reviewMessageProducer;
	}

	@GetMapping
	public ResponseEntity<List<Review>> getAllReviews(@RequestParam Long companyId){
		List<Review> reviews = reviewService.getAllReviews(companyId);
		return new ResponseEntity<List<Review>>(reviews, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> addReview(@RequestParam Long companyId,@RequestBody Review review){
		boolean isReviewSaved = reviewService.addReview(companyId, review);
		if(isReviewSaved) {
			reviewMessageProducer.sendMessage(review);
			return new ResponseEntity<String>("Review Added Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReview(@PathVariable Long reviewId){
		Review review = reviewService.getReview(reviewId);
		return new ResponseEntity<Review>(review, HttpStatus.OK);
	}
	
	@PutMapping("/{reviewId}")
	public ResponseEntity<String> updateReview(@PathVariable Long reviewId,@RequestBody Review review){
		boolean isReviewUpated = reviewService.updateReview(reviewId, review);
		if(isReviewUpated) {
			return new ResponseEntity<String>("Review Updated Successfully", HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
		boolean isReviewDeleted = reviewService.deleteReview(reviewId);
		if(isReviewDeleted) {
			return new ResponseEntity<String>("Review Deleted Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/averageRating")
	public double getAverageReview(@RequestParam Long companyId) {
		List<Review> reviewList = reviewService.getAllReviews(companyId);
		return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
		
	}

}
