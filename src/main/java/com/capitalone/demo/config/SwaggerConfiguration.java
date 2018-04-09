package com.capitalone.demo.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnWebApplication
@EnableSwagger2
public class SwaggerConfiguration {
	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("CapitalOne Demo Application REST API").description("CapitalOne Demo Application REST API")
				.version("1.0.0")
				.build();
	}

	@Bean
	public Docket customImplementation() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.capitalone.demo.controller")).build().apiInfo(apiInfo());
	}

}
