package com.xd.pre.modules.file.config;

import com.xd.pre.modules.file.FileHeaderCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FileCheckConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new FileHeaderCheckInterceptor())
                .addPathPatterns("/sys-file/**");
    }
}
