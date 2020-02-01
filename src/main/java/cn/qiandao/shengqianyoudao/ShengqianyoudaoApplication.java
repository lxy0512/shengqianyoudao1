package cn.qiandao.shengqianyoudao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@EnableSwagger2
@MapperScan("cn.qiandao.shengqianyoudao.mapper")
@SpringBootApplication
public class ShengqianyoudaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShengqianyoudaoApplication.class, args);
    }
}
