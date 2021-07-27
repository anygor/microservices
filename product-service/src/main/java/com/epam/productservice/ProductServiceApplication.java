package com.epam.productservice;

import com.epam.productservice.storage.StorageProperties;
import com.epam.productservice.storage.StorageService;
import com.epam.productservice.validators.GroupValidator;
import com.epam.productservice.validators.ProductValidator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.Validator;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrix
@EnableConfigurationProperties(StorageProperties.class)
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	public Validator productValidator() {
		return new ProductValidator();
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			//			storageService.deleteAll();
			storageService.init();
		};
	}


	@Bean
	public Validator groupValidator() {
		return new GroupValidator();
	}

}
