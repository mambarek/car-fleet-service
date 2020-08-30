package com.it2go.micro.carfleetservice.bootstrap;

import com.it2go.micro.carfleetservice.generated.domain.Car;
import com.it2go.micro.carfleetservice.generated.domain.Car.StatusEnum;
import com.it2go.micro.carfleetservice.services.CarService;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CarLoader implements CommandLineRunner {

  private final CarService carService;

  @Override
  public void run(String... args) throws Exception {

    Car bmw = new Car();
    bmw.setName("BMW");
    bmw.setDescription("A powerful vehicle");
    bmw.setPublicId(UUID.randomUUID());
    bmw.setMileage(100000);
    bmw.setReservedFrom(OffsetDateTime.now());
    bmw.setReservedTo(OffsetDateTime.now());
    bmw.setStatus(StatusEnum.RESERVED);

    carService.saveNewCar(bmw);

    Car mercedes = new Car();
    mercedes.setName("Mercedes");
    mercedes.setDescription("A very nice vehicle");
    mercedes.setPublicId(UUID.randomUUID());
    mercedes.setMileage(150000);
    mercedes.setReservedFrom(OffsetDateTime.now());
    mercedes.setReservedTo(OffsetDateTime.now());
    mercedes.setStatus(StatusEnum.UNDER_REPAIR);

    carService.saveNewCar(mercedes);

    Car toyota = new Car();
    toyota.setName("Toyota");
    toyota.setDescription("The best Off-road vehicle");
    toyota.setPublicId(UUID.randomUUID());
    toyota.setPublicId(UUID.randomUUID());
    toyota.setMileage(180000);
    toyota.setReservedFrom(OffsetDateTime.now());
    toyota.setReservedTo(OffsetDateTime.now());
    toyota.setStatus(StatusEnum.STOPPED);

    carService.saveNewCar(toyota);
  }
}
