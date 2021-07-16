package com.epam.mentoring.gateway;

import com.epam.mentoring.gateway.balancer.RibbonConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "gateway", configuration = RibbonConfiguration.class)
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/product/**")
						.uri("lb://PRODUCT-SERVICE")
						.id("productModule"))
				.route(r -> r.path("/group/**")
						.uri("lb://PRODUCT-SERVICE")
						.id("groupModule"))
				.route(r -> r.path("/cart/**")
						.uri("lb://BACKEND-SERVICE")
						.id("cartModule"))
				.route(r -> r.path("/order/**")
						.uri("lb://BACKEND-SERVICE")
						.id("orderModule"))
				.build();

	}

}
