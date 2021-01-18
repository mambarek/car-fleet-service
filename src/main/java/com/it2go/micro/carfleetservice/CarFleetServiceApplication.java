package com.it2go.micro.carfleetservice;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EntityScan(basePackages = {"com.it2go.util.jpa.entities", "com.it2go.micro.carfleetservice.persistence.jpa.entities"})
public class CarFleetServiceApplication {

  @PostConstruct
  public void init(){
    // Setting Spring Boot SetTimeZone
    //TimeZone.setDefault(TimeZone.getTimeZone("Europe/Berlin"));
  }

  public static void main(String[] args) {
    SpringApplication.run(CarFleetServiceApplication.class, args);
  }
}
