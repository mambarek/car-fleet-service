package com.it2go.micro.carfleetservice.services.impl;

import com.it2go.micro.carfleetservice.generated.domain.CarReservation;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationEventEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.mapper.CarReservationMapper;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.repositories.CarRepository;
import com.it2go.micro.carfleetservice.persistence.jpa.repositories.CarReservationRepository;
import com.it2go.micro.carfleetservice.services.CarReservationService;
import com.it2go.micro.carfleetservice.services.CarReservationStateChangeInterceptor;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

/**
 * created by mmbarek on 09.11.2020.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CarReservationServiceImpl implements CarReservationService {

  public static final String RESERVATION_ID_HEADER = "reservation_id";

  private final CarReservationRepository carReservationRepository;
  private final StateMachineFactory<CarReservationStatusEnum, CarReservationEventEnum> stateMachineFactory;
  private final CarReservationStateChangeInterceptor carReservationStateChangeInterceptor;
  private final CarReservationMapper reservationMapper;

  @Override
  public CarReservation createReservation(CarReservation carReservation) {
    CarReservationEntity carReservationEntity = reservationMapper
        .carReservationToCarReservationEntity(carReservation);
    CarReservationEntity savedEntity = carReservationRepository.save(carReservationEntity);

    return reservationMapper.carReservationEntityToCarReservation(savedEntity);
  }

  @Override
  public CarReservation findReservation(UUID publicId) {
    CarReservationEntity reservation = carReservationRepository.findByPublicId(publicId);

    return reservationMapper.carReservationEntityToCarReservation(reservation);
  }

  @Override
  public StateMachine<CarReservationStatusEnum, CarReservationEventEnum> confirmReservation(UUID reservationPublicId) {
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = buildStateMachine(
        reservationPublicId);

    sendEvent(reservationPublicId, stateMachine, CarReservationEventEnum.CONFIRM_RESERVATION);

    return stateMachine;
  }

  @Override
  public StateMachine<CarReservationStatusEnum, CarReservationEventEnum> cancelReservation(UUID reservationPublicId) {
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = buildStateMachine(
        reservationPublicId);
    sendEvent(reservationPublicId, stateMachine, CarReservationEventEnum.CANCEL_RESERVATION);
    return stateMachine;
  }

  @Override
  public StateMachine<CarReservationStatusEnum, CarReservationEventEnum> rejectReservation(UUID reservationPublicId) {
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = buildStateMachine(
        reservationPublicId);
    sendEvent(reservationPublicId, stateMachine, CarReservationEventEnum.REJECT_RESERVATION);
    return stateMachine;
  }

  @Override
  public StateMachine<CarReservationStatusEnum, CarReservationEventEnum> allocateReservation(UUID reservationPublicId) {
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = buildStateMachine(
        reservationPublicId);
    sendEvent(reservationPublicId, stateMachine, CarReservationEventEnum.ALLOCATE_RESERVATION);
    return stateMachine;
  }

  @Override
  public StateMachine<CarReservationStatusEnum, CarReservationEventEnum> startReservation(UUID reservationPublicId) {
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = buildStateMachine(
        reservationPublicId);
    sendEvent(reservationPublicId, stateMachine, CarReservationEventEnum.START_RESERVATION);
    return stateMachine;
  }

  @Override
  public StateMachine<CarReservationStatusEnum, CarReservationEventEnum> finishReservation(UUID reservationPublicId) {
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = buildStateMachine(
        reservationPublicId);
    sendEvent(reservationPublicId, stateMachine, CarReservationEventEnum.FINISH_RESERVATION);
    return stateMachine;
  }

  @Override
  public StateMachine<CarReservationStatusEnum, CarReservationEventEnum> createReservationInvoice(UUID reservationPublicId) {
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = buildStateMachine(
        reservationPublicId);
    sendEvent(reservationPublicId, stateMachine, CarReservationEventEnum.CREATE_INVOICE);
    return stateMachine;
  }

  @Override
  public StateMachine<CarReservationStatusEnum, CarReservationEventEnum> payReservation(UUID reservationPublicId) {
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = buildStateMachine(
        reservationPublicId);
    sendEvent(reservationPublicId, stateMachine, CarReservationEventEnum.PAY_INVOICE);
    return stateMachine;
  }

  private void sendEvent(UUID publicId,
      StateMachine<CarReservationStatusEnum, CarReservationEventEnum> sm,
      CarReservationEventEnum event) {
    Message<CarReservationEventEnum> msg = MessageBuilder
        .withPayload(event)
        .setHeader(RESERVATION_ID_HEADER, publicId)
        .build();

    sm.sendEvent(msg);
  }

  private StateMachine<CarReservationStatusEnum, CarReservationEventEnum> buildStateMachine(
      UUID reservationId) {
    CarReservationEntity carReservationEntity = carReservationRepository
        .findByPublicId(reservationId);

    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = stateMachineFactory
        .getStateMachine(reservationId);

    stateMachine.stop();

    stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> {
      sma.addStateMachineInterceptor(carReservationStateChangeInterceptor);
      Optional.ofNullable(carReservationEntity).ifPresent(reservation ->
          sma.resetStateMachine(
              new DefaultStateMachineContext<>(
                  CarReservationStatusEnum.fromValue(reservation.getStatus()), null, null, null))
      );
    });

    stateMachine.getExtendedState().getVariables().put(CarReservationServiceImpl.RESERVATION_ID_HEADER, carReservationEntity);

    stateMachine.start();

    return stateMachine;
  }
}
