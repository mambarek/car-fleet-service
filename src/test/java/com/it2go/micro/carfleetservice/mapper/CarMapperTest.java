package com.it2go.micro.carfleetservice.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.carfleetservice.generated.domain.Car;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 04.03.2021.
 */
@DisplayName("CarMapper Tests -")
class CarMapperTest {

  CarMapper carMapper;
  Car validCar;
  CarEntity validCarEntity;

  @BeforeEach
  void setUp(){
    carMapper = new CarMapperImpl();

    validCarEntity = CarEntity.builder()
        .publicId(UUID.randomUUID())
        .brand("X5")
        .color("Gray")
        .build();

    validCar = new Car()
        .publicId(UUID.randomUUID())
        .brand("X5")
        .color("Gray");
  }

  @Test
  void carEntityToCar() {

    Car car = carMapper.carEntityToCar(validCarEntity);
    assertNotNull(car);
    assertEquals(validCarEntity.getPublicId(), car.getPublicId());
  }

  @Test
  void carToCarEntity() {

    CarEntity carEntity = carMapper.carToCarEntity(validCar);
    assertNotNull(carEntity);
    assertEquals(carEntity.getPublicId(), validCar.getPublicId());
  }

  @Test
  void updateCarEntity() {
    Car car = carMapper.carEntityToCar(validCarEntity);
    car.setBrand("X1");

    CarEntity carEntity = carMapper.updateCarEntity(validCarEntity, car);
    assertNotNull(carEntity);
    assertEquals(carEntity.getBrand(), car.getBrand());
  }
}
