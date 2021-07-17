package com.epam.mentoring.gateway.balancer;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

// atm this balancer doesn't work correctly because it cannot get the request context neither for getting eureka instances nor for getting routing key
// if you delete this package, the default round robin thing will work
public class CustomBalanceRule extends BestAvailableRule {

    private static final Logger logger = LogManager.getLogger(CustomBalanceRule.class);

    private String getHeader() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            return request.getHeader("routingKey");
        }
        logger.debug("Not called in the context of an HTTP request");
        return null;
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
//        return allServers.get(allServers.size() - 1);
        return super.choose(key);
    }
}
