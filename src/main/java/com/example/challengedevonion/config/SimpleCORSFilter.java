package com.example.challengedevonion.config;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class SimpleCORSFilter implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;
    private final LocalDateSerializer localDateSerializer;
    private final LocalDateDeserializer localDateDeserializer;

    public SimpleCORSFilter(ObjectMapper objectMapper, LocalDateSerializer localDateSerializer, LocalDateDeserializer localDateDeserializer) {
        this.objectMapper = objectMapper;
        this.localDateSerializer = localDateSerializer;
        this.localDateDeserializer = localDateDeserializer;
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT");
    }

    private MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        SimpleModule module = new SimpleModule("Natanael", Version.unknownVersion());
        module.addSerializer(LocalDate.class, localDateSerializer);
        module.addDeserializer(LocalDate.class, localDateDeserializer);
        objectMapper.registerModule(module);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter());
        converters.add(customJackson2HttpMessageConverter());
        converters.add(new FormHttpMessageConverter());
    }


}