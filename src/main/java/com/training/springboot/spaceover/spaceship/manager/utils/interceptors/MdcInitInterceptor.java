package com.training.springboot.spaceover.spaceship.manager.utils.interceptors;

import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.SERVICE_OPERATION;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.TRACE_ID;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.TRACE_ID_HEADER;
import static com.training.springboot.spaceover.spaceship.manager.utils.constants.SpaceShipManagerConstant.UNDEFINED_SERVICE_OPERATION;

import com.training.springboot.spaceover.spaceship.manager.utils.annotatations.ServiceOperation;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class MdcInitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put(TRACE_ID, Optional.ofNullable(request.getHeader(TRACE_ID_HEADER)).orElse(UUID.randomUUID().toString()));
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Optional<ServiceOperation> serviceOperationAnnotation = Optional
                    .ofNullable(handlerMethod.getMethodAnnotation(ServiceOperation.class));
            MDC.put(SERVICE_OPERATION, serviceOperationAnnotation.isPresent() ? serviceOperationAnnotation.get().value()
                    : UNDEFINED_SERVICE_OPERATION);
        }
        return true;
    }

}
