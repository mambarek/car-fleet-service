package com.it2go.micro.carfleetservice.bootstrap;

import com.it2go.micro.carfleetservice.generated.domain.Car;
import com.it2go.micro.carfleetservice.generated.domain.Car.EngineTypeEnum;
import com.it2go.micro.carfleetservice.generated.domain.Car.FuelTypeEnum;
import com.it2go.micro.carfleetservice.generated.domain.Car.StatusEnum;
import com.it2go.micro.carfleetservice.services.CarService;
import java.time.LocalDate;
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
    bmw.setBrand("BMW");
    bmw.setModel("X5");
    bmw.setManufacturingDate(LocalDate.of(2019, 3, 1));
    bmw.setEngineType(EngineTypeEnum.FUEL);
    bmw.setFuelType(FuelTypeEnum.DIESEL);
    bmw.setColor("Silver");
    bmw.setDescription("A powerful vehicle");
    bmw.setPublicId(UUID.randomUUID());
    bmw.setMileage(20000);
    bmw.setStatus(StatusEnum.READY);

    carService.createCar(bmw);

    Car mercedes = new Car();
    mercedes.setBrand("Mercedes");
    mercedes.setModel("GLE Coupe");
    mercedes.setManufacturingDate(LocalDate.of(2020, 4, 15));
    mercedes.setEngineType(EngineTypeEnum.FUEL);
    mercedes.setFuelType(FuelTypeEnum.DIESEL);
    mercedes.setColor("Black");
    mercedes.setDescription("A very nice vehicle");
    mercedes.setPublicId(UUID.randomUUID());
    mercedes.setMileage(15000);
    mercedes.setStatus(StatusEnum.UNDER_REPAIR);

    carService.createCar(mercedes);

    Car tesla = new Car();
    tesla.setBrand("Tesla");
    tesla.setModel("Model X");
    tesla.setManufacturingDate(LocalDate.of(2019, 6, 25));
    tesla.setEngineType(EngineTypeEnum.ELECTRIC);
    tesla.setColor("White");
    tesla.setDescription("A very nice vehicle");
    tesla.setPublicId(UUID.randomUUID());
    tesla.setMileage(25000);
    tesla.setStatus(StatusEnum.READY);

    carService.createCar(tesla);

    Car toyota = new Car();
    toyota.setBrand("Toyota");
    toyota.setModel("Yaris");
    toyota.setManufacturingDate(LocalDate.of(2020, 4, 15));
    toyota.setEngineType(EngineTypeEnum.HYBRID);
    toyota.setFuelType(FuelTypeEnum.PETROL);
    toyota.setColor("Red");
    toyota.setDescription("The best Off-road vehicle");
    toyota.setPublicId(UUID.randomUUID());
    toyota.setPublicId(UUID.randomUUID());
    toyota.setMileage(180000);
    toyota.setStatus(StatusEnum.BROKEN);

    carService.createCar(toyota);
  }
}
