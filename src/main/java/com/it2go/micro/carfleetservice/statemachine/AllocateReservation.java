package com.it2go.micro.carfleetservice.statemachine;

import com.it2go.micro.carfleetservice.generated.domain.CarReservationEventEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.repositories.CarReservationRepository;
import com.it2go.micro.carfleetservice.services.impl.CarReservationServiceImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 07.03.2021.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class AllocateReservation implements
    Action<CarReservationStatusEnum, CarReservationEventEnum> {

  private final CarReservationRepository carReservationRepository;

  @Override
  public void execute(
      StateContext<CarReservationStatusEnum, CarReservationEventEnum> stateContext) {

    System.out.println(">>> testAction was called!!!");
    System.out.println(stateContext.getStateMachine().getState().getId());

    UUID publicId = stateContext.getExtendedState()
        .get(CarReservationServiceImpl.RESERVATION_ID_HEADER, UUID.class);

    System.out.println("-- Allocate Reservation for publicId: " + publicId);

    if(publicId == null) return;

    CarReservationEntity reservationEntity = carReservationRepository.findByPublicId(publicId);

    System.out.println("-- Reservation status: " + reservationEntity.getStatus());
  }
}
