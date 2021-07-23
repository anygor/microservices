package com.epam.productservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "recommendation-service")
public interface RecommendationFeignClient {
	@RequestMapping(method = RequestMethod.GET, value = "/recommend")
	List<Long> getRecommendedProductIds(@RequestParam long productId);
}
