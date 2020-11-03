package com.it2go.micro.carfleetservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Cors configuration globally not fine grained in Controllers So here we allow all but you can
 * change this
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    // cors allow all origins or set a filter. now we allow all
    registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    // without this w get exception calling swagger-ui.html
    // https://github.com/springdoc/springdoc-openapi/issues/236
    registry.viewResolver(new InternalResourceViewResolver());
  }
}
