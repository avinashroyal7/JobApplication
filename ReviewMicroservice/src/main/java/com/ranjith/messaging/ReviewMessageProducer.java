package com.ranjith.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.ranjith.dto.ReviewMessage;
import com.ranjith.entity.Review;

@Service
public class ReviewMessageProducer {

	private final RabbitTemplate rabbitTemplate;

	public ReviewMessageProducer(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendMessage(Review review) {
		ReviewMessage reviewMessage = new ReviewMessage();
		reviewMessage.setId(review.getId());
		reviewMessage.setCompanyId(review.getCompanyId());
		reviewMessage.setTitle(review.getTitle());
		reviewMessage.setDescription(review.getDescription());
		reviewMessage.setRating(review.getRating());
		rabbitTemplate.convertAndSend("companyRatingQueue",reviewMessage);
	}
	
	
	
	
}
