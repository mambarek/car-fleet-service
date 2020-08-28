package com.it2go.micro.carfleetservice.services;

import com.it2go.micro.carfleetservice.domain.Car;
import java.util.List;
import java.util.UUID;

public interface CarService {

  List<Car> findAllCars();

  Car findCarByPublicId(UUID publicId);

  Car saveNewCar(Car car);

  Car updateCar(Car car);

  void deleteCar(UUID publicId);

  Long countCars();
}
