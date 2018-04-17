package com.patsnap.spring.cloud.ribbon.predicate;

import com.patsnap.spring.cloud.ribbon.support.RibbonFilterContext;
import com.patsnap.spring.cloud.ribbon.support.RibbonFilterContextHolder;

import org.springframework.cloud.consul.discovery.ConsulServer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Match the server instance against the attributes registered through with consul.
 *
 * @author songhaifeng
 * @date 2018/4/11
 */
public class MetadataAwarePredicate extends ConsulServerPredicate {

    @Override
    protected boolean apply(ConsulServer server) {
        final RibbonFilterContext context = RibbonFilterContextHolder.getCurrentContext();
        final Set<Map.Entry<String, String>> attributes = Collections.unmodifiableSet(context.getAttributes().entrySet());
        final Map<String, String> metadata = server.getMetadata();
        final Map<String, String> upperCaseMetadata = new HashMap<>(metadata.size());
        metadata.forEach((key, value) -> upperCaseMetadata.put(key.toUpperCase(), value.toUpperCase()));
        return upperCaseMetadata.entrySet().containsAll(attributes);
    }
}
