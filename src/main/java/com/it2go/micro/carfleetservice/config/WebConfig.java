package com.it2go.micro.carfleetservice.config;
/**
 *-------------------------------------------------------------------------------------
 * IMPORTANT  IMPORTANT  IMPORTANT
 *
 * Adding @EnableWebMvc make Date Format to d
 * https://stackoverflow.com/questions/54932574/adding-enablewebmvc-in-springboot-changes-date-output-from-string-to-array
 *
 * By default, SerializationFeature.WRITE_DATES_AS_TIMESTAMPS is enabled, and it gives an array of
 * date-time components similar to yours (f.e., [2014,3,30,12,30,23,123456789] instead of "2014-03-30T12:30:23.123456789").
 *
 * With adding @EnableWebMvc you're saying to Spring that you want to get the full control over
 * Spring MVC configuration. So you need to configure the Jackson object mapper manually.
 *
 * This Config for CORS is replaced with CorsConfig that n ot use @EnableWebMvc
 * */


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
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
/*@Configuration
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


}*/
public class WebConfig {}
