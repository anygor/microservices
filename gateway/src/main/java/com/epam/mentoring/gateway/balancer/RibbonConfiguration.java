package com.epam.mentoring.gateway.balancer;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClient(name = "PRODUCT-SERVICE", configuration = RibbonConfiguration.class)
public class RibbonConfiguration {

	@Bean
	public IPing ribbonPing() {
		return new PingUrl();
	}

	@Bean
	public IRule ribbonRule() {
		return new CustomBalanceRule();
	}
}
