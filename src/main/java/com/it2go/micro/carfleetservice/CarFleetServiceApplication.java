package com.it2go.micro.carfleetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EntityScan(basePackages = {"com.it2go.util.jpa.entities", "com.it2go.micro.carfleetservice.persistence.jpa.entities"})
public class CarFleetServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(CarFleetServiceApplication.class, args);
  }
}
