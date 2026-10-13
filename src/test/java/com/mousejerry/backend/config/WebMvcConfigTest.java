package com.mousejerry.backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class WebMvcConfigTest {

    @Test
    void addResourceHandlers_RegistersUploadFilesPath() {
        WebMvcConfig config = new WebMvcConfig();
        ReflectionTestUtils.setField(config, "uploadDir", "uploads");

        ResourceHandlerRegistry registry = mock(ResourceHandlerRegistry.class);
        ResourceHandlerRegistration registration = mock(ResourceHandlerRegistration.class);
        when(registry.addResourceHandler("/api/v1/upload/files/**")).thenReturn(registration);
        when(registration.addResourceLocations(anyString())).thenReturn(registration);

        config.addResourceHandlers(registry);

        verify(registry).addResourceHandler("/api/v1/upload/files/**");
        verify(registration).addResourceLocations(anyString());
    }
}
