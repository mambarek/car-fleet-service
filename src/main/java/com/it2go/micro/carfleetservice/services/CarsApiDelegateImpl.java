package com.it2go.micro.carfleetservice.services;

import com.it2go.micro.carfleetservice.generated.controller.CarsApiDelegate;
import com.it2go.micro.carfleetservice.generated.domain.Car;
import com.it2go.micro.carfleetservice.generated.domain.SearchResult;
import com.it2go.micro.carfleetservice.generated.domain.SearchTemplate;
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

  @Override
  public ResponseEntity<SearchResult> search(SearchTemplate searchTemplate) {
    return null;
  }

  @Override
  public ResponseEntity<Void> deleteCar(UUID publicId) {
    carService.deleteCar(publicId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Car> getCarByPublicId(UUID publicId) {
    Car carByPublicId = carService.findCarByPublicId(publicId);
    if(carByPublicId == null) return ResponseEntity.notFound().build();

    return ResponseEntity.ok(carByPublicId);
  }

  @Override
  public ResponseEntity<Car> updateCar(UUID publicId, Car car) {
    if(!publicId.equals(car.getPublicId())) return ResponseEntity.badRequest().build();

    Car updateCar = carService.updateCar(car);
    return ResponseEntity.ok(updateCar);
  }

  @Override
  public ResponseEntity<Car> saveCar(Car car) {
    return ResponseEntity.ok(carService.saveNewCar(car));
  }
}
