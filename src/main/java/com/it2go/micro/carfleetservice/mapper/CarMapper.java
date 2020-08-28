package com.it2go.micro.carfleetservice.mapper;

import com.it2go.micro.carfleetservice.domain.Car;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarMapper {

  Car carEntityToCar(CarEntity carEntity);

  @Mapping(source = "status.value", target = "status")
  CarEntity carToCarEntity(Car car);

  List<Car> carEntitiesToCars(List<CarEntity> carEntities);
}
