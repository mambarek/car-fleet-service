package com.it2go.micro.carfleetservice.mapper;

import com.it2go.micro.carfleetservice.generated.domain.CarReservation;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * created by mmbarek on 09.11.2020.
 */
@Mapper
public interface CarReservationMapper {

  CarReservation carReservationEntityToCarReservation(CarReservationEntity carReservationEntity);

  @Mapping(source = "status.value", target = "status")
  CarReservationEntity carReservationToCarReservationEntity(CarReservation carReservation);
}
