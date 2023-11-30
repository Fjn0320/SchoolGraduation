package com.example.demo.common;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Configuration
//定义一个拦截规则
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())//配置拦截器的规则
                .addPathPatterns("/**")//配置要拦截的路径
                .excludePathPatterns("/login/**");
        super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }


}
