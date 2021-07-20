package com.epam.mentoring.gateway.balancer;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import com.netflix.zuul.context.RequestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomBalanceRule extends BestAvailableRule {

    private static final Logger logger = LogManager.getLogger(CustomBalanceRule.class);

    private String getHeader() {
        RequestContext currentContext = RequestContext.getCurrentContext();
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
            Optional<DiscoveryEnabledServer> discoveryEnabledServerOptional = allServers.stream().filter(server -> server.getInstanceInfo().getMetadata().get("routingKey").equals(header)).findAny();
            if (discoveryEnabledServerOptional.isPresent()) {
                return discoveryEnabledServerOptional.get();
            }
        }
        return super.choose(key);
    }
}
