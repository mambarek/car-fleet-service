package com.it2go.micro.carfleetservice.persistence.jpa.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * created by mmbarek on 04.03.2021.
 */
@DisplayName("CarRepository Tests -")
@DataJpaTest
class CarRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  CarRepository carRepository;

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
    entityManager.flush();

    Optional<CarEntity> byPublicId = carRepository.findByPublicId(carEntity.getPublicId());
    assertNotNull(byPublicId);
  }
}
