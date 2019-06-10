package com.shf.spring.cloud.ribbon.configuration;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Enable ribbon route rule with discovery metadata,{@link DefaultRibbonMetadataRouteConfiguration} is default configuration for registered with consul.
 *
 * @author songhaifeng
 * @date 2018/4/13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(DefaultRibbonMetadataRouteConfiguration.class)
public @interface EnableMetadataRoute {
}
