package com.epam.productservice.feign;

import com.epam.productservice.feign.fallback.RecommendationFallback;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RecommendationFallbackFactory implements FallbackFactory<RecommendationFeignClient> {
	@Override
	public RecommendationFeignClient create(Throwable throwable) {
		return new RecommendationFallback(throwable);
	}
}
