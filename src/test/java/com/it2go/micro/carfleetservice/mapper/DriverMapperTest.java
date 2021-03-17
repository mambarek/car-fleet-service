package com.it2go.micro.carfleetservice.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.carfleetservice.generated.domain.Driver;
import com.it2go.micro.carfleetservice.generated.domain.PersonData;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.DriverEntity;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 04.03.2021.
 */
class DriverMapperTest {

  DriverMapper driverMapper;
  Driver validDriver;
  DriverEntity validDriverEntity;

  @BeforeEach
  void setUp(){
    driverMapper = new DriverMapperImpl();
    validDriver = new Driver()
        .publicId(UUID.randomUUID())
        .data(new PersonData().firstName("Max").lastName("Mustermann"));

    com.it2go.util.jpa.entities.PersonData personData = new com.it2go.util.jpa.entities.PersonData();
    personData.setFirstName("Max");
    personData.setLastName("Mustermann");
    validDriverEntity = DriverEntity.builder()
        .id(1L)
        .publicId(UUID.randomUUID())
        .data(personData)
        .build();

  }

  @Test
  void driverEntityToDriver() {
    Driver driver = driverMapper.driverEntityToDriver(validDriverEntity);

    assertNotNull(driver);
    assertEquals(driver.getPublicId(), validDriverEntity.getPublicId());
    assertEquals(driver.getData().getFirstName(), validDriverEntity.getData().getFirstName());
  }

  @Test
  void driverToDriverEntity() {
    DriverEntity driverEntity = driverMapper.driverToDriverEntity(validDriver);

    assertNotNull(driverEntity);
    assertEquals(driverEntity.getPublicId(), validDriver.getPublicId());
    assertEquals(driverEntity.getData().getFirstName(), validDriver.getData().getFirstName());
  }
}
