package com.shf.spring.cloud.ribbon.configuration;

import com.shf.spring.cloud.ribbon.rule.ZoneAvoidanceWithMetadataAwareRule;
import com.shf.spring.cloud.ribbon.support.RibbonFilterContextHolder;

import com.netflix.loadbalancer.IRule;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.consul.ConditionalOnConsulEnabled;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Default configuration for ribbon load balance with server instances's tag(named TAG) registered through with consul.
 *
 * @author songhaifeng
 * @date 2018/4/13
 */
@Configuration
public class DefaultRibbonMetadataRouteConfiguration {

    private static final String DEFAULT_HEADER_FILTER = "headerFilter";

    @Bean
    @ConditionalOnConsulEnabled
    public IRule metadataAwareRule() {
        return new ZoneAvoidanceWithMetadataAwareRule();
    }

    @Bean(name = DEFAULT_HEADER_FILTER)
    @ConditionalOnMissingBean(name = DEFAULT_HEADER_FILTER)
    public FilterRegistrationBean headerFilter() {
        return new FilterRegistrationBean(new DefaultHeaderFilter());
    }

    public static class DefaultHeaderFilter extends OncePerRequestFilter {
        static final String TAG_NAME = "TAG";

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            if (request.getHeader(TAG_NAME) != null) {
                RibbonFilterContextHolder.getCurrentContext().add(TAG_NAME, request.getHeader(TAG_NAME).toUpperCase());
                try {
                    filterChain.doFilter(request, response);
                } finally {
                    RibbonFilterContextHolder.clearCurrentContext();
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    public static class RibbonFilterHeaderInterceptor implements ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            request.getHeaders().add(DefaultRibbonMetadataRouteConfiguration.DefaultHeaderFilter.TAG_NAME,
                    RibbonFilterContextHolder.getCurrentContext().get(DefaultRibbonMetadataRouteConfiguration.DefaultHeaderFilter.TAG_NAME));
            return execution.execute(request, body);
        }
    }

}
