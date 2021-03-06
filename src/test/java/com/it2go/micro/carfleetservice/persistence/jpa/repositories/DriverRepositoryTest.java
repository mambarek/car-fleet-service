package com.it2go.micro.carfleetservice.persistence.jpa.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.carfleetservice.persistence.jpa.entities.DriverEntity;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * created by mmbarek on 04.03.2021.
 */
@DisplayName("DriverRepository Tests -")
@DataJpaTest
class DriverRepositoryTest {

  @Autowired
  TestEntityManager entityManager;

  @Autowired
  DriverRepository driverRepository;

  @Test
  void findByPublicId() {
    com.it2go.util.jpa.entities.PersonData personData = new com.it2go.util.jpa.entities.PersonData();
    personData.setFirstName("Max");
    personData.setLastName("Mustermann");

    DriverEntity driverEntity = DriverEntity.builder()
        .publicId(UUID.randomUUID())
        .data(personData)
        .build();

    entityManager.persist(driverEntity);
    entityManager.flush();

    DriverEntity byPublicId = driverRepository.findByPublicId(driverEntity.getPublicId());
    assertNotNull(byPublicId);
    assertEquals(byPublicId.getPublicId(), driverEntity.getPublicId());
  }
}
