package com.it2go.micro.carfleetservice.services.impl;

import com.it2go.micro.carfleetservice.generated.domain.Car;
import com.it2go.micro.carfleetservice.mapper.CarMapper;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.repositories.CarRepository;
import com.it2go.micro.carfleetservice.services.CarService;
import com.it2go.micro.carfleetservice.services.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

  private static final Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

  private final CarMapper carMapper;
  private final CarRepository carRepository;

  @Override
  public List<Car> findAllCars() {
    LOG.info("-- findAllCars() ");
    List<Car> result = new ArrayList<>();
    carRepository.findAll().forEach(carEntity -> result.add(carMapper.carEntityToCar(carEntity)));

    return result;
  }

  @Override
  public Car findCarByPublicId(UUID publicId) {
    LOG.info(String.format("-- findCarByPublicId() publicId: [%s]", publicId));
    CarEntity carEntity = carRepository.findByPublicId(publicId)
        .orElseThrow(EntityNotFoundException::new);

    LOG.info(String.format("-- findCarByPublicId() publicId: [%s] successfully found", publicId));
    return carMapper.carEntityToCar(carEntity);
  }

  @Override
  public Car createCar(Car car) {
    LOG.info(String.format("-- saveNewCar() publicId: [%s]", car.getPublicId()));
    CarEntity carEntity = carMapper.carToCarEntity(car);
    CarEntity savedEntity = carRepository.save(carEntity);
    LOG.info(String.format("-- saveNewCar() with publicId: [%s] successfully saved", car.getPublicId()));
    return carMapper.carEntityToCar(savedEntity);
  }

  @Override
  public Car updateCar(Car car) {
    LOG.info(String.format("-- updateCar() publicId: [%s]", car.getPublicId()));
    CarEntity byPublicId = carRepository.findByPublicId(car.getPublicId())
        .orElseThrow(EntityNotFoundException::new);

    CarEntity carEntity = carMapper.updateCarEntity(byPublicId, car);
    CarEntity updatedCarEntity = carRepository.save(carEntity);
    LOG.info(String.format("-- updateCar() with publicId: [%s] successfully updated", car.getPublicId()));
    return carMapper.carEntityToCar(updatedCarEntity);
  }

  @Override
  public void deleteCar(UUID publicId) {
    LOG.info(String.format("-- deleteCar() publicId: [%s]", publicId));
    CarEntity carEntity = carRepository.findByPublicId(publicId)
        .orElseThrow(EntityNotFoundException::new);
    LOG.info(String.format("-- deleteCar() with publicId: [%s] successfully deleted", publicId));
    carRepository.delete(carEntity);
  }

  @Override
  public Long countCars() {
    return carRepository.count();
  }
}
