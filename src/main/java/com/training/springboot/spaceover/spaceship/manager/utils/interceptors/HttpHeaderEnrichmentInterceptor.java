package com.training.springboot.spaceover.spaceship.manager.utils.interceptors;

import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SERVICE_OPERATION_HEADER;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.TRACE_ID;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.TRACE_ID_HEADER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpHeaderEnrichmentInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.addHeader(TRACE_ID_HEADER, MDC.get(TRACE_ID));
        response.addHeader(SERVICE_OPERATION_HEADER, MDC.get(SERVICE_OPERATION));
        return true;
    }

}
