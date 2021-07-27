package com.epam.productservice.feign;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class RecommendationFallback implements RecommendationFeignClient {

	@Override
	public List<Long> getRecommendedProductIds(long productId) {
		return Stream.of(1l, 2l).collect(Collectors.toList());
	}
}
