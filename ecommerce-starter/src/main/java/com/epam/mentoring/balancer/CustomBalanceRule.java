package com.epam.mentoring.balancer;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.ribbon.eureka.DomainExtractingServerList;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomBalanceRule extends WeightedResponseTimeRule {

	private static final Logger logger = LogManager.getLogger(CustomBalanceRule.class);

	private String getHeader() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		return request.getHeader("routingKey");
	}

	@Override
	public Server choose(Object key) {
		List<DiscoveryEnabledServer> allServers = getLoadBalancer().getAllServers().
				stream().map(server -> (DiscoveryEnabledServer) server).
				filter(discoveryEnabledServer -> discoveryEnabledServer.getInstanceInfo().getStatus().equals(InstanceInfo.InstanceStatus.UP))
				.collect(Collectors.toList());
		String header = this.getHeader();
		if (header != null) {
			int serviceIndex = Integer.parseInt(header.substring(1));
			if (allServers.size() >= serviceIndex) {
				return allServers.get(serviceIndex - 1);
			}
		}
		return super.choose(key);
	}
}
