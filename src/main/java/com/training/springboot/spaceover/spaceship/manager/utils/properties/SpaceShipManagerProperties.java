package com.training.springboot.spaceover.spaceship.manager.utils.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class SpaceShipManagerProperties {

	@Value("${spring.application.name:spacemission-manager}")
	private String applicationName;

	@Value("${server.servlet.context-path:#{null}}")
	private String servletContextPath;

	@Value("${open-api.header.pagination.enabled:false}")
	private boolean openApiHeaderPaginationEnabled;

}
