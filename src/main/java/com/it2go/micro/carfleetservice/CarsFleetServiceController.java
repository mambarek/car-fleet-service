package com.it2go.micro.carfleetservice;

import com.it2go.micro.carfleetservice.domain.Car;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cars")
public class CarsFleetServiceController {

  @GetMapping
  public ResponseEntity<Void> testResponse() {
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{publicId}")
  public ResponseEntity<Car> getCarByPublicId(@PathVariable("publicId") String publicId) {
    Car car = new Car();
    car.setName("Toyota");
    car.setDescription("Toyota Geländewagen");
    car.setPublicId(UUID.randomUUID());

    return new ResponseEntity<>(car, HttpStatus.OK);
  }
}
