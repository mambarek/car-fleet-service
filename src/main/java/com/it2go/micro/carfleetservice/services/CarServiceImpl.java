package com.it2go.micro.carfleetservice.services;

import com.it2go.micro.carfleetservice.domain.Car;
import com.it2go.micro.carfleetservice.mapper.CarMapper;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.repositories.CarRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

  private final CarMapper carMapper;
  private final CarRepository carRepository;

  @Override
  public List<Car> findAllCars() {
    List<Car> result = new ArrayList<>();
    carRepository.findAll().forEach(carEntity -> result.add(carMapper.carEntityToCar(carEntity)));
    return result;
  }

  @Override
  public Car findCarByPublicId(UUID publicId) {
    return null;
  }

  @Override
  public Car saveNewCar(Car car) {
    CarEntity carEntity = carMapper.carToCarEntity(car);
    CarEntity savedEntity = carRepository.save(carEntity);

    return carMapper.carEntityToCar(savedEntity);
  }

  @Override
  public Car updateCar(Car car) {
    return null;
  }

  @Override
  public void deleteCar(UUID publicId) {

  }

  @Override
  public Long countCars() {
    return null;
  }
}
