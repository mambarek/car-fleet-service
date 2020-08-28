package com.it2go.micro.carfleetservice.services;

import com.it2go.micro.carfleetservice.CarsApiDelegate;
import com.it2go.micro.carfleetservice.domain.Car;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

@Component
@AllArgsConstructor
public class CarsApiDelegateImpl implements CarsApiDelegate {

  private final CarService carService;

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  @Override
  public ResponseEntity<List<Car>> getCars() {
    return ResponseEntity.ok(carService.findAllCars());
  }
}
