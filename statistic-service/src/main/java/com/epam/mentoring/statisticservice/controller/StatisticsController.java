package com.epam.mentoring.statisticservice.controller;

import com.epam.mentoring.statisticservice.model.User;
import com.epam.mentoring.statisticservice.model.stats.StatisticsUnit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;

@Controller
public class StatisticsController {

	private final List<StatisticsUnit> stats = new LinkedList<>();

	@RequestMapping(value = "/addStats", method = RequestMethod.POST)
	public void addStats(@RequestBody StatisticsUnit statisticsUnit) {
		stats.add(statisticsUnit);
	}

	@RequestMapping(value = "/getStats", method = RequestMethod.GET)
	@ResponseBody
	public List<StatisticsUnit> getStatistics() {
		return stats;
	}
}
