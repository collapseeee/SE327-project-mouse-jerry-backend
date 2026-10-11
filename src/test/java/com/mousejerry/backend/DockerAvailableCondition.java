package com.mousejerry.backend;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.DockerClientFactory;

public class DockerAvailableCondition implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        try {
            if (DockerClientFactory.instance().isDockerAvailable()) {
                return ConditionEvaluationResult.enabled("Docker is available");
            }
        } catch (Throwable ignored) {
            // Docker client threw exception when checking daemon
        }
        return ConditionEvaluationResult.disabled("Docker is not available in current environment");
    }
}
