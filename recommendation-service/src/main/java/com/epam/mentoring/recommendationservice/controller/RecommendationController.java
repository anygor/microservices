package com.epam.mentoring.recommendationservice.controller;

import com.epam.mentoring.recommendationservice.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RecommendationController {

	@Autowired
	private RecommendationService recommendationService;

	@RequestMapping(value = "/recommend", method = RequestMethod.GET)
	@ResponseBody
	public List<Long> recommend(@RequestParam long productId) {
		return recommendationService.getRecommendedProductIds(productId);
	}
}
