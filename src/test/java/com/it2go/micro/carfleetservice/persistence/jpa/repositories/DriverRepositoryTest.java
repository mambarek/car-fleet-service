package com.it2go.micro.carfleetservice.persistence.jpa.repositories;

import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.carfleetservice.generated.domain.Address;
import com.it2go.micro.carfleetservice.generated.domain.Driver;
import com.it2go.micro.carfleetservice.generated.domain.Gender;
import com.it2go.micro.carfleetservice.generated.domain.PersonData;
import com.it2go.micro.carfleetservice.mapper.DriverMapper;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.DriverEntity;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * created by mmbarek on 10.11.2020.
 */
@SpringBootTest
class DriverRepositoryTest {

  @Autowired
  DriverRepository driverRepository;
  @Autowired
  DriverMapper driverMapper;

  @Test
  void findByPublicId() {
    Driver driver = new Driver();
    UUID publicId = UUID.randomUUID();
    driver.setPublicId(publicId);
    PersonData data = new PersonData();
    Address address = new Address();
    address.setPublicId(UUID.randomUUID());
    address.setBuildingNr("5");
    address.setCity("New York");
    address.setCountryCode("US");
    address.setStreetOne("Street number one");
    address.setZipCode("78965");
    data.setAddress(address);
    data.setBirthDate(LocalDate.now());
    data.setEmail("mbarek@gmail.com");
    data.setFirstName("Ali");
    data.setLastName("mbarek");
    data.setGender(Gender.MALE);
    driver.setData(data);

    DriverEntity driverEntity = driverMapper.driverToDriverEntity(driver);

    driverRepository.save(driverEntity);

    DriverEntity byPublicId = driverRepository.findByPublicId(publicId);
    System.out.println(byPublicId);
  }
}
