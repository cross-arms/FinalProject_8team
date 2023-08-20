package com.techit.withus.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig
{
    @Bean
    public OpenAPI openAPI()
    {
        Info info = new Info()
                .title("WITH US")
                .description("WITH US API DESCRIPTION")
                .version("V0.0.1");

        return new OpenAPI()
                .info(info);
    }
}
