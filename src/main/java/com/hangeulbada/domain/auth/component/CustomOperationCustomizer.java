package com.hangeulbada.domain.auth.component;

import io.swagger.v3.oas.models.Operation;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;

@Component
public class CustomOperationCustomizer implements OperationCustomizer {
    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        // 여기에서 특정 조건에 따라 보안 요구 사항을 제거합니다.
        // 예를 들어, 특정 어노테이션이 있는 경우 보안 요구 사항을 제거할 수 있습니다.
        if (handlerMethod.hasMethodAnnotation(UnsecuredAPI.class)) {
            operation.setSecurity(new ArrayList<>()); // 보안 요구 사항 제거
        }
        return operation;
    }
}

