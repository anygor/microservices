package com.epam.mentoring.balancer;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
