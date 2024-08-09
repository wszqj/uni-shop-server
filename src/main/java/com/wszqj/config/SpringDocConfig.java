package com.wszqj.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SpringDocConfig
 * @description: TODO
 * @date 2024年07月30日
 * @author: wszqj
 * @version: 1.0
 */
@OpenAPIDefinition(
        info = @Info(
                title = "吴沈终秦晋致富之路",
                version = "v1.0",
                description = "This is wszqj’s way to get rich",
                contact = @Contact(name = "wszqj", email = "wszqj0913@gmail.com")
        )
)
@Configuration  //表明这是一个注解类
public class SpringDocConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/user/**", "/home/**","/order/**")
                .build();
    }

/*    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin") // 另一个分组名称
                .pathsToMatch("/admin/**", "/management/**") // 匹配的多个路径
                .build();
    }

    @Bean
    public GroupedOpenApi otherApi() {
        return GroupedOpenApi.builder()
                .group("other") // 另一个分组名称
                .pathsToMatch("/other/**", "/misc/**") // 匹配的多个路径
                .build();
    }*/
}
