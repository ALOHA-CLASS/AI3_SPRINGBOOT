package com.aloha.swagger.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publApi() {
        return GroupedOpenApi.builder()
                        // 그룹명 설정
                        .group("group-aloha")
                        // 경로 설정
                        .pathsToMatch("/api/**", "/posts/**")
                        // 경로 제외
                        .pathsToExclude("/admin/**")
                        .build()
                        ;
    }

    @Bean
    public OpenAPI springTestOpenAPI() {
        return new OpenAPI()
                    .info(new Info().title("Test Project API")
                                    .description("테스트 프로젝트 API 입니다.")
                                    .version("v0.0.1")
                    );
    }
    
}
