package com.atguigu.edu.statistic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket webApiConfig(){
        /*
        swagger - 文档生成工具
        使用swagger后 会将项目中的各种参数 方法等自动生成文档信息
        方便调用者进行查看
        进行相关的配置即可
        导入依赖信息
        生成对应配置类文件 - 进行配置
         */
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .build();
    }


    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("网站-讲师管理API文档")
                .description("本文档描述了讲师管理微服务接口定义")
                .version("1.0")
                .contact(new Contact("Helen", "http://atguigu.com", "55317332@qq.com"))
                .build();
    }


}
