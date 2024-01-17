package com.gostop.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String API_NAME = "go-stop API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "GO-STOP API Document";

    @Bean
    public OpenAPI goStopAPI() {
        return new OpenAPI()
                .info(new Info().title(API_NAME)
                        .version(API_VERSION)
                        .description(API_DESCRIPTION));
    }

}
