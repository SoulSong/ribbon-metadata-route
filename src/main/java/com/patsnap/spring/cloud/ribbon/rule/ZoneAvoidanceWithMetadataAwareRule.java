package com.patsnap.spring.cloud.ribbon.rule;

import com.patsnap.spring.cloud.ribbon.predicate.ConsulServerPredicate;
import com.patsnap.spring.cloud.ribbon.predicate.MetadataAwarePredicate;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerPredicate;
import com.netflix.loadbalancer.AvailabilityPredicate;
import com.netflix.loadbalancer.CompositePredicate;
import com.netflix.loadbalancer.ZoneAvoidancePredicate;

/**
 * A rule that uses the a {@link CompositePredicate} to filter servers based on zone and availability. The primary predicate is
 * composed of a {@link MetadataAwarePredicate}、 {@link ZoneAvoidancePredicate} and {@link AvailabilityPredicate},
 * with the fallbacks to {@link AvailabilityPredicate} and an "always true" predicate returned from {@link AbstractServerPredicate#alwaysTrue()}
 *
 * @author songhaifeng
 * @date 2018/4/11
 */
public class ZoneAvoidanceWithMetadataAwareRule extends MetadataAwareRule {

    private CompositePredicate compositePredicate;

    public ZoneAvoidanceWithMetadataAwareRule() {
        this.compositePredicate = createCompositePredicate(new MetadataAwarePredicate(), new ZoneAvoidancePredicate(this, null), new AvailabilityPredicate(this, null));
    }

    /**
     * Creates the composite predicate with fallback strategies.
     *
     * @param consulServerPredicate  the consul server predicate
     * @param zoneAvoidancePredicate the zone avoidance predicate
     * @param availabilityPredicate  the availability predicate
     * @return the composite predicate
     */
    private CompositePredicate createCompositePredicate(ConsulServerPredicate consulServerPredicate, ZoneAvoidancePredicate zoneAvoidancePredicate, AvailabilityPredicate availabilityPredicate) {
        return CompositePredicate.withPredicates(consulServerPredicate, zoneAvoidancePredicate, availabilityPredicate).addFallbackPredicate(availabilityPredicate)
                .addFallbackPredicate(AbstractServerPredicate.alwaysTrue())
                .build();
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        MetadataAwarePredicate metadataAwarePredicate = new MetadataAwarePredicate();
        ZoneAvoidancePredicate zonePredicate = new ZoneAvoidancePredicate(this, clientConfig);
        AvailabilityPredicate availabilityPredicate = new AvailabilityPredicate(this, clientConfig);
        this.compositePredicate = createCompositePredicate(metadataAwarePredicate, zonePredicate, availabilityPredicate);
    }

    @Override
    public AbstractServerPredicate getPredicate() {
        return this.compositePredicate;
    }
}
