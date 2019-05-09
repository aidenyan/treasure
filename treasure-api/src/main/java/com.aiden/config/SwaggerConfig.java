package com.aiden.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurerAdapter {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        List<MediaType> mediaTypes = new ArrayList<>();
        MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
        mediaTypes.add(mediaType);
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
        converter.setSupportedMediaTypes(mediaTypes);
        converter.setFastJsonConfig(config);
        converters.add(converter);
    }


    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2).groupName("测试样例").select()
                .apis(RequestHandlerSelectors.basePackage("com.aiden.controller"))
                .build()
                .apiInfo(apiInfo()).pathMapping("/");

    }

    private ApiInfo apiInfo() {
        /**
         * api的节本信息
         */
        return new ApiInfoBuilder().title("RestAPI Docs")
                .termsOfServiceUrl("")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }


}