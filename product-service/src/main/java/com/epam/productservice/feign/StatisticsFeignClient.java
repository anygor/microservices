package com.epam.productservice.feign;

import com.epam.productservice.models.User;
import com.epam.productservice.models.stats.StatisticsUnit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "statistic-service", fallback = StatisticsFallback.class)
public interface StatisticsFeignClient {
	@RequestMapping(method = RequestMethod.GET, value = "/getStats")
	List<StatisticsUnit> getStats();

	@RequestMapping(method = RequestMethod.POST, value = "/addStats")
	void addStats(@RequestBody StatisticsUnit statisticsUnit);
}
