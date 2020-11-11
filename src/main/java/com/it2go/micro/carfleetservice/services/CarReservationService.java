package com.it2go.micro.carfleetservice.services;

import com.it2go.micro.carfleetservice.generated.domain.CarReservation;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationEventEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import java.util.UUID;
import org.springframework.statemachine.StateMachine;

/**
 * created by mmbarek on 09.11.2020.
 */
public interface CarReservationService {

  CarReservation createReservation(CarReservation carReservation);
  CarReservation findReservation(UUID publicId);

  // state transition handling
  StateMachine<CarReservationStatusEnum, CarReservationEventEnum> confirmReservation(UUID reservationPublicId);
  StateMachine<CarReservationStatusEnum, CarReservationEventEnum> cancelReservation(UUID reservationPublicId);
  StateMachine<CarReservationStatusEnum, CarReservationEventEnum> rejectReservation(UUID reservationPublicId);
  StateMachine<CarReservationStatusEnum, CarReservationEventEnum> allocateReservation(UUID reservationPublicId);
  StateMachine<CarReservationStatusEnum, CarReservationEventEnum> startReservation(UUID reservationPublicId);
  StateMachine<CarReservationStatusEnum, CarReservationEventEnum> finishReservation(UUID reservationPublicId);
  StateMachine<CarReservationStatusEnum, CarReservationEventEnum> createReservationInvoice(UUID reservationPublicId);
  StateMachine<CarReservationStatusEnum, CarReservationEventEnum> payReservation(UUID reservationPublicId);
}
