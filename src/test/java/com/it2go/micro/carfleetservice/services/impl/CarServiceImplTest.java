package com.it2go.micro.carfleetservice.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.carfleetservice.generated.domain.Car;
import com.it2go.micro.carfleetservice.generated.domain.Car.EngineTypeEnum;
import com.it2go.micro.carfleetservice.mapper.CarMapper;
import com.it2go.micro.carfleetservice.mapper.CarMapperImpl;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.repositories.CarRepository;
import com.it2go.micro.carfleetservice.services.CarService;
import com.it2go.micro.carfleetservice.services.impl.CarServiceImplTest.CarServiceConfiguration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;

/**
 * created by mmbarek on 17.03.2021.
 */
@SpringJUnitConfig(classes = {CarServiceConfiguration.class})
class CarServiceImplTest {

  @Configuration
  static class CarServiceConfiguration{

    @Bean
    public CarMapper carMapper(){
      return new CarMapperImpl();
    }

    @Bean
    public CarService carService(CarMapper carMapper, CarRepository carRepository){
      return new CarServiceImpl(carMapper, carRepository);
    }
  }

  @Autowired
  CarService carService;

  @Autowired
  CarMapper carMapper;

  @MockBean
  CarRepository carRepository;

  Car validCar;
  CarEntity validCarEntity;

  @BeforeEach
  void setup(){
    validCar = new Car();
    validCar.setModel("X5");
    validCar.setBrand("BMW");
    validCar.setPublicId(UUID.randomUUID());
    validCar.setDescription("Nice car");
    validCar.setColor("Grey");
    validCar.setEngineType(EngineTypeEnum.ELECTRIC);

    validCarEntity = carMapper.carToCarEntity(validCar);
    validCarEntity.setId(1L);
    validCarEntity.setPublicId(UUID.randomUUID());
  }

  @AfterEach
  void teardown(){
    reset(carRepository);
  }

  @Test
  void findAllCars() {
    given(carRepository.findAll()).willReturn(List.of(validCarEntity));
    List<Car> allCars = carService.findAllCars();
    assertThat(allCars).hasSize(1);
  }

  @Test
  void findCarByPublicId() {
    given(carRepository.findByPublicId(any(UUID.class))).willReturn(Optional.ofNullable(validCarEntity));

    Car carByPublicId = carService.findCarByPublicId(validCarEntity.getPublicId());
    assertThat(carByPublicId).isNotNull();
    assertThat(carByPublicId.getPublicId()).isEqualTo(validCarEntity.getPublicId());
  }

  @Test
  void createCar() {
    given(carRepository.save(any(CarEntity.class))).willReturn(validCarEntity);

    Car savedCar = carService.createCar(validCar);
    assertThat(savedCar).isNotNull();
    assertThat(savedCar.getPublicId()).isEqualTo(validCarEntity.getPublicId());
  }

  @Test
  void updateCar() {
    given(carRepository.findByPublicId(any(UUID.class))).willReturn(Optional.ofNullable(validCarEntity));
    given(carRepository.save(any(CarEntity.class))).willReturn(validCarEntity);

    Car updateCar = carService.updateCar(validCar);
    assertThat(updateCar).isNotNull();
  }

  @Test
  void deleteCar() {
    given(carRepository.findByPublicId(any(UUID.class))).willReturn(Optional.ofNullable(validCarEntity));
    doNothing().when(carRepository).delete(any(CarEntity.class));

    carService.deleteCar(validCarEntity.getPublicId());
  }

  @Test
  void countCars() {
    given(carRepository.count()).willReturn(1L);

    Long countCars = carService.countCars();
    assertThat(countCars).isEqualTo(1L);
  }
}
