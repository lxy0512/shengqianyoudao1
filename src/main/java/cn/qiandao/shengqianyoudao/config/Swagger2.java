package cn.qiandao.shengqianyoudao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("钱道测试分组1")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.qiandao.shengqianyoudao.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("钱道api文档")
                .description("钱道app地址：")
                .termsOfServiceUrl("地址。。。。。。")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("钱道测试分组2")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.qiandao.shengqianyoudao.controller"))
                .paths(PathSelectors.ant("/show/**"))
                .build();
    }
}
