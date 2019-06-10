package com.shf.spring.cloud.ribbon.predicate;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.PredicateKey;

import org.springframework.cloud.consul.discovery.ConsulServer;

import javax.annotation.Nullable;

/**
 * A template server predicate to adjust discovered server instance which registered with consul to be applied.
 *
 * @author songhaifeng
 * @date 2018/4/11
 */
public abstract class ConsulServerPredicate extends AbstractServerPredicate {

    /**
     * Returns the result of applying this predicate to {@code input}.
     */
    @Override
    public boolean apply(@Nullable PredicateKey input) {
        return input != null
                && input.getServer() instanceof ConsulServer
                && apply((ConsulServer) input.getServer());
    }

    /**
     * Returns whether the specific {@link ConsulServer} matches this predicate.
     *
     * @param server the discovered server
     * @return whether the server matches the predicate
     */
    protected abstract boolean apply(ConsulServer server);
}
