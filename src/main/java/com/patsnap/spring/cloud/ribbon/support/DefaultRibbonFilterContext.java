package com.patsnap.spring.cloud.ribbon.support;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implement {@link RibbonFilterContext} , store filter attributes by map.
 *
 * @author songhaifeng
 * @date 2018/4/11
 */
public class DefaultRibbonFilterContext implements RibbonFilterContext {

    private final Map<String, String> attributes = new HashMap<>();

    @Override
    public RibbonFilterContext add(String key, String value) {
        attributes.put(key, value);
        return this;
    }

    @Override
    public String get(String key) {
        return attributes.get(key);
    }

    @Override
    public RibbonFilterContext remove(String key) {
        attributes.remove(key);
        return this;
    }

    @Override
    public Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }
}
