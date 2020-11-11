package com.it2go.micro.carfleetservice.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.carfleetservice.generated.domain.CarReservation;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.DriverEntity;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * created by mmbarek on 09.11.2020.
 */
@SpringBootTest
class CarReservationMapperTest {

  @Autowired
  CarReservationMapper reservationMapper;

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

    System.out.println(carReservation);
  }

  @Test
  void carReservationToCarReservationEntity() {
  }
}
