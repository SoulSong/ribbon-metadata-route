package com.shf.spring.cloud.ribbon.rule;

import com.shf.spring.cloud.ribbon.predicate.ConsulServerPredicate;
import com.shf.spring.cloud.ribbon.predicate.MetadataAwarePredicate;

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
