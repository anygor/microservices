package com.epam.mentoring.recommendationservice.service;

import com.epam.mentoring.recommendationservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RecommendationService {
	public List<Long> getRecommendedProductIds(Product product) {
		LinkedList<Long> ids = new LinkedList<>();
		ids.add(1L);
		ids.add(4L);
		ids.add(8L);
		ids.add(8L);
		ids.add(product.getId());
		return ids;
	}
}
