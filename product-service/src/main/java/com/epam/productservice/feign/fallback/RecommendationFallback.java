package com.epam.productservice.feign.fallback;

import com.epam.productservice.feign.RecommendationFeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import feign.hystrix.HystrixDelegatingContract;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RecommendationFallback implements RecommendationFeignClient {

	private final Throwable cause;

	public RecommendationFallback(Throwable cause) {
		this.cause = cause;
	}
	@Override
	public List<Long> getRecommendedProductIds(@PathVariable long productId) {
		return Stream.of(1L, 2L, 3L).collect(Collectors.toList());
	}
}
