package com.patsnap.spring.cloud.ribbon.rule;

import com.patsnap.spring.cloud.ribbon.predicate.ConsulServerPredicate;
import com.patsnap.spring.cloud.ribbon.predicate.MetadataAwarePredicate;

/**
 * A metadata aware {@link ConsulServerRule} implementation.
 *
 * @author songhaifeng
 * @date 2018/4/11
 */
public class MetadataAwareRule extends ConsulServerRule {

    public MetadataAwareRule() {
        this(new MetadataAwarePredicate());
    }

    public MetadataAwareRule(ConsulServerPredicate predicate) {
        super(predicate);
    }

}
