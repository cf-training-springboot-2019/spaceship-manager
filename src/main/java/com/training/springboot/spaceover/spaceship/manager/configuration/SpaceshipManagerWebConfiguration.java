package com.training.springboot.spaceover.spaceship.manager.configuration;

import com.training.springboot.spaceover.spaceship.manager.utils.interceptors.HttpHeaderEnrichmentInterceptor;
import com.training.springboot.spaceover.spaceship.manager.utils.interceptors.HttpLoggerInterceptor;
import com.training.springboot.spaceover.spaceship.manager.utils.interceptors.MdcInitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SpaceshipManagerWebConfiguration {

	@Bean
	public WebMvcConfigurer webMvcConfigurationInitializer(MdcInitInterceptor mdcInitHandler,
			HttpHeaderEnrichmentInterceptor headerEnrichmentHandlerInterceptor,
			HttpLoggerInterceptor httpLoggerInterceptor) {
		return new WebMvcConfigurer() {
			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(mdcInitHandler);
				registry.addInterceptor(headerEnrichmentHandlerInterceptor);
				registry.addInterceptor(httpLoggerInterceptor);
			}
		};
	}

	@Bean
	public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}

}