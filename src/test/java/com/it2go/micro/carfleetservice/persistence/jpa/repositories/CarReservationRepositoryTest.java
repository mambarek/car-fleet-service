package com.it2go.micro.carfleetservice.persistence.jpa.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.DriverEntity;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * created by mmbarek on 04.03.2021.
 */
@DisplayName("CarReservationRepository Tests -")
@DataJpaTest
class CarReservationRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  CarReservationRepository carReservationRepository;

  @Test
  void findByPublicId() {
    CarEntity carEntity = CarEntity.builder()
        .publicId(UUID.randomUUID())
        .color("Blue")
        .brand("BMW")
        .model("X5")
        .manufacturingDate(LocalDate.now())
        .engineType("Diesel")
        .build();

    entityManager.persist(carEntity);

    com.it2go.util.jpa.entities.PersonData personData = new com.it2go.util.jpa.entities.PersonData();
    personData.setFirstName("Max");
    personData.setLastName("Mustermann");

    DriverEntity driverEntity = DriverEntity.builder()
        .publicId(UUID.randomUUID())
        .data(personData)
        .build();

    entityManager.persist(driverEntity);

    CarReservationEntity carReservationEntity = CarReservationEntity.builder()
        .car(carEntity)
        .driver(driverEntity)
        .publicId(UUID.randomUUID())
        .reservedFrom(OffsetDateTime.of(2020,10,1,12,0,0,0, ZoneOffset.UTC))
        .reservedTo(OffsetDateTime.of(2020,10,15,12,0,0,0, ZoneOffset.UTC))
        .status(CarReservationStatusEnum.CONFIRMED.toString())
        .build();

    entityManager.persist(carReservationEntity);
    entityManager.flush();

    CarReservationEntity byPublicId = carReservationRepository
        .findByPublicId(carReservationEntity.getPublicId());

    assertNotNull(byPublicId);
    assertEquals(byPublicId.getPublicId(), carReservationEntity.getPublicId());
    assertEquals(byPublicId.getCar().getPublicId(), carReservationEntity.getCar().getPublicId());
    assertEquals(byPublicId.getDriver().getPublicId(), carReservationEntity.getDriver().getPublicId());
  }
}
