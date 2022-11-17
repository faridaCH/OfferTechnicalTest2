package com.offertest.UserAPI.configurations;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.offertest.UserAPI.controllers.ConstAppRoot.APP_ROOT;


@EnableAutoConfiguration
@EnableSwagger2
public class SwaggerConfiguration {

    public Docket getDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(new ApiInfoBuilder().description(" Offer Technical Test documentation  ").title(" Offer Technical Test (User)  REST API ").build()).groupName(" REST API V1 ").select().apis(RequestHandlerSelectors.basePackage("com.offertest.UserAPI")).paths(PathSelectors.ant(APP_ROOT + "/**")).build();
    }


}
