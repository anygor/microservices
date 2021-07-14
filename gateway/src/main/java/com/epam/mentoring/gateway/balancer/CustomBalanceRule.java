package com.epam.mentoring.gateway.balancer;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class was implemented for balancing product-service requests back at ecommerce app
 * Now due to multiple services called from the gateway this balancing rule may behave wrong so it isn't used in the Ribbon config
 *
 * @author Andrey_Gordeev
 */
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
