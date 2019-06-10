package com.shf.spring.cloud.ribbon.support;

/**
 * Ribbon filter context holder.
 *
 * @author songhaifeng
 * @date 2018/4/11
 */
public class RibbonFilterContextHolder {

    /**
     * Stores the {@link RibbonFilterContext} for current thread.
     */
    private static final ThreadLocal<RibbonFilterContext> CONTEXT_HOLDER = new InheritableThreadLocal<RibbonFilterContext>() {
        @Override
        protected RibbonFilterContext initialValue() {
            return new DefaultRibbonFilterContext();
        }
    };

    /**
     * Retrieves the current thread bound instance of {@link RibbonFilterContext}.
     *
     * @return the current context
     */
    public static RibbonFilterContext getCurrentContext() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * Clears the current context.
     */
    public static void clearCurrentContext() {
        CONTEXT_HOLDER.remove();
    }
}
