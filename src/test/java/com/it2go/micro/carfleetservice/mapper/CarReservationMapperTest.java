package com.it2go.micro.carfleetservice.mapper;

import com.it2go.micro.carfleetservice.generated.domain.Car;
import com.it2go.micro.carfleetservice.generated.domain.CarReservation;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.generated.domain.Driver;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.DriverEntity;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * created by mmbarek on 09.11.2020.
 */
@DisplayName("CarReservationMapper tests - ")
class CarReservationMapperTest {

  CarReservationMapper reservationMapper;

  @BeforeEach
  void setUp(){
    reservationMapper = new CarReservationMapperImpl();
  }

  @Test
  void carReservationEntityToCarReservation() {
    CarReservationEntity carReservationEntity = CarReservationEntity.builder()
        .car(CarEntity.builder().publicId(UUID.randomUUID()).build())
        .driver(DriverEntity.builder().publicId(UUID.randomUUID()).build())
        .publicId(UUID.randomUUID())
        .reservedFrom(OffsetDateTime.of(2020,10,1,12,0,0,0, ZoneOffset.UTC))
        .reservedTo(OffsetDateTime.of(2020,10,15,12,0,0,0, ZoneOffset.UTC))
        .status(CarReservationStatusEnum.CONFIRMED.toString())
        .build();

    CarReservation carReservation = reservationMapper
        .carReservationEntityToCarReservation(carReservationEntity);

    assertThat(carReservation).isNotNull();
    assertThat(carReservation.getPublicId()).isEqualTo(carReservationEntity.getPublicId());
    assertThat(carReservation.getCar().getPublicId()).isEqualTo(carReservationEntity.getCar().getPublicId());
    assertThat(carReservation.getDriver().getPublicId()).isEqualTo(carReservationEntity.getDriver().getPublicId());
  }

  @Test
  void carReservationToCarReservationEntity() {
    Car car = new Car().publicId(UUID.randomUUID());
    Driver driver = new Driver().publicId(UUID.randomUUID());
    CarReservation carReservation = new CarReservation()
        .car(car)
        .driver(driver)
        .publicId(UUID.randomUUID())
        .reservedFrom(OffsetDateTime.of(2020,10,1,12,0,0,0, ZoneOffset.UTC))
        .reservedTo(OffsetDateTime.of(2020,10,15,12,0,0,0, ZoneOffset.UTC))
        .status(CarReservationStatusEnum.CONFIRMED);

    CarReservationEntity carReservationEntity = reservationMapper
        .carReservationToCarReservationEntity(carReservation);

    assertThat(carReservationEntity).isNotNull();
    assertThat(carReservationEntity.getPublicId()).isEqualTo(carReservation.getPublicId());
    assertThat(carReservationEntity.getCar().getPublicId()).isEqualTo(carReservation.getCar().getPublicId());
    assertThat(carReservationEntity.getDriver().getPublicId()).isEqualTo(carReservation.getDriver().getPublicId());
  }
}
