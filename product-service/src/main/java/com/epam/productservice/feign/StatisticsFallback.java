package com.epam.productservice.feign;

import com.epam.productservice.models.User;
import com.epam.productservice.models.stats.StatisticsUnit;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatisticsFallback implements StatisticsFeignClient {
	@Override
	public List<StatisticsUnit> getStats() {
		return null;
	}

	@Override
	public void addStats(StatisticsUnit statisticsUnit) {

	}
}
