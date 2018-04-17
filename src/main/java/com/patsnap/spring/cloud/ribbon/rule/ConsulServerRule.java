package com.patsnap.spring.cloud.ribbon.rule;

import com.patsnap.spring.cloud.ribbon.predicate.ConsulServerPredicate;

import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PredicateBasedRule;

import org.springframework.util.Assert;

/**
 * A simple {@link IRule} for matching the consul server instances. The actual matching is being performed by the
 * registered instance of {@link ConsulServerPredicate} allowing to adjust the actual matching strategy.
 *
 * @author songhaifeng
 * @date 2018/4/11
 */
public abstract class ConsulServerRule extends PredicateBasedRule {

    private final CompositePredicate compositePredicate;

    /**
     * Creates new instance of {@link ConsulServerRule} class with specific predicate.
     *
     * @param consulServerPredicate the consul server predicate, can't be null
     * @throws IllegalArgumentException if {@code discoveryEnabledPredicate} is {@code null}
     */
    public ConsulServerRule(ConsulServerPredicate consulServerPredicate) {
        Assert.notNull(consulServerPredicate, "Parameter 'consulServerPredicate' can't be null");
        this.compositePredicate = createCompositePredicate(consulServerPredicate, new AvailabilityPredicate(this, null));
    }

    /**
     * Creates the composite predicate with fallback strategies.
     *
     * @param consulServerPredicate the consul server predicate
     * @param availabilityPredicate the availability predicate
     * @return the composite predicate
     */
    private CompositePredicate createCompositePredicate(ConsulServerPredicate consulServerPredicate, AvailabilityPredicate availabilityPredicate) {
        return CompositePredicate.withPredicates(consulServerPredicate, availabilityPredicate).addFallbackPredicate(availabilityPredicate)
                .addFallbackPredicate(AbstractServerPredicate.alwaysTrue())
                .build();
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return compositePredicate;
    }
}
