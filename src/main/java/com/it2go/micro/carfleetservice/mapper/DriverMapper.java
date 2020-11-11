package com.it2go.micro.carfleetservice.mapper;

import com.it2go.micro.carfleetservice.generated.domain.Driver;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.DriverEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * created by mmbarek on 10.11.2020.
 */
@Mapper
public interface DriverMapper {

/*  @Mapping(source = "firstName", target = "data.firstName")
  @Mapping(source = "lastName", target = "data.lastName")
  @Mapping(source = "birthDate", target = "data.birthDate")
  @Mapping(source = "gender", target = "data.gender")
  @Mapping(source = "email", target = "data.email")
  @Mapping(source = "address", target = "data.address")*/
  Driver driverEntityToDriver(DriverEntity driverEntity);

/*  @Mapping(target = "firstName", source = "data.firstName")
  @Mapping(target = "lastName", source = "data.lastName")
  @Mapping(target = "birthDate", source = "data.birthDate")
  @Mapping(target = "gender", source = "data.gender")
  @Mapping(target = "email", source = "data.email")
  @Mapping(target = "address", source = "data.address")*/
  DriverEntity driverToDriverEntity(Driver driver);
}
