package com.boot.config;

import com.boot.utils.DateUtil;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.text.ParseException;

/**
 * http://localhost:8088/swagger-ui.html 路径
 * http://localhost:8088/doc.html 新路径
 */

@Configuration
@EnableSwagger2
public class Swagger2 {
    public Docket craeteRestApi() throws ParseException {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boot.controller"))   //指定controller包
                .paths(PathSelectors.any())                                         //所有controller
                .build()
                ;
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title(" BOOT INFO")
                .contact(new Contact("jacket", "http://www.baidu.com", "ddd"))
                .description("boot api文档")
                .version("1.0.0")
                .termsOfServiceUrl("http://www.baidu.com")
                .build()
                ;
    }
}
