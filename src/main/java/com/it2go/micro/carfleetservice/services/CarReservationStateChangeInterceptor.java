package com.it2go.micro.carfleetservice.services;

import com.it2go.micro.carfleetservice.generated.domain.CarReservationEventEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.repositories.CarReservationRepository;
import com.it2go.micro.carfleetservice.services.impl.CarReservationServiceImpl;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 09.11.2020.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class CarReservationStateChangeInterceptor extends
    StateMachineInterceptorAdapter<CarReservationStatusEnum, CarReservationEventEnum> {

  private final CarReservationRepository carReservationRepository;

  @Override
  public void preStateChange(State<CarReservationStatusEnum, CarReservationEventEnum> state,
      Message<CarReservationEventEnum> message,
      Transition<CarReservationStatusEnum, CarReservationEventEnum> transition,
      StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine) {

    log.info("preStateChange was called!!!");
    if (message == null) {
      return;
    }

    UUID reservationId = (UUID) message.getHeaders()
        .get(CarReservationServiceImpl.RESERVATION_ID_HEADER);

    if (reservationId == null) {
      return;
    }

    CarReservationEntity reservation = carReservationRepository
        .findByPublicId(reservationId);

    if (reservation != null) {
      reservation.setStatus(state.getId().toString());
      carReservationRepository.saveAndFlush(reservation);
    }
  }

  @Override
  public void postStateChange(State<CarReservationStatusEnum, CarReservationEventEnum> state,
      Message<CarReservationEventEnum> message,
      Transition<CarReservationStatusEnum, CarReservationEventEnum> transition,
      StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine) {
    log.info("postStateChange was called!!!");
  }
}
