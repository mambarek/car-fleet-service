package com.it2go.micro.carfleetservice.mapper;

import com.it2go.micro.carfleetservice.generated.domain.Car;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface CarMapper {

  Car carEntityToCar(CarEntity carEntity);

  @Mapping(source = "status.value", target = "status")
  CarEntity carToCarEntity(Car car);

  CarEntity updateCarEntity(@MappingTarget CarEntity carEntity, Car car);
}
