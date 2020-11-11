package com.it2go.micro.carfleetservice.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.it2go.micro.carfleetservice.generated.domain.CarReservation;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationEventEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.mapper.CarReservationMapper;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.DriverEntity;
import com.it2go.micro.carfleetservice.services.CarReservationService;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

/**
 * created by mmbarek on 09.11.2020.
 */
@SpringBootTest
class CarReservationServiceImplTest {

  @Autowired
  CarReservationMapper reservationMapper;
  @Autowired
  CarReservationService carReservationService;

  private UUID uuid = UUID.fromString("be23a3d5-46fc-4609-84ac-72eebc559c42");

  @BeforeEach
  void setUp() {
    CarReservationEntity carReservationEntity = CarReservationEntity.builder()
        //.car(CarEntity.builder().publicId(UUID.randomUUID()).build())
        //.driver(DriverEntity.builder().publicId(UUID.randomUUID()).build())
        .publicId(uuid)
        .reservedFrom(OffsetDateTime.of(2020,10,1,12,0,0,0, ZoneOffset.UTC))
        .reservedTo(OffsetDateTime.of(2020,10,15,12,0,0,0, ZoneOffset.UTC))
        .status(CarReservationStatusEnum.NEW.toString())
        .build();

    CarReservation carReservation = reservationMapper
        .carReservationEntityToCarReservation(carReservationEntity);

    carReservationService.createReservation(carReservation);
  }

  @AfterEach
  void tearDown() {
  }

  @Test
  void createReservation() {
  }

  @Test
  void confirmReservation() {
    carReservationService.confirmReservation(uuid);

    CarReservation reservation = carReservationService.findReservation(uuid);
    System.out.println(reservation.getStatus());
  }

  @Test
  void cancelReservation() {
  }

  @Test
  void rejectReservation() {
  }

  @Test
  void allocateReservation() {
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> sm = carReservationService
        .allocateReservation(uuid);

    CarReservation reservation = carReservationService.findReservation(uuid);
    System.out.println("--> Statemachine status 1: " + sm.getState().getId());
    System.out.println("--> Reservation status 1 " + reservation.getStatus());
    // it should still NEW because of statemachine
    assertThat(reservation.getStatus()).isEqualTo(CarReservationStatusEnum.NEW);

    // now confirm than allocate it sholud allocate the reservation
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> sm2 =carReservationService.confirmReservation(uuid);
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> sm3 =carReservationService.allocateReservation(uuid);
    reservation = carReservationService.findReservation(uuid);
    System.out.println(reservation.getStatus());
    assertThat(reservation.getStatus()).isEqualTo(CarReservationStatusEnum.ALLOCATED);
  }

  @Test
  void testCreateReservation() {
  }

  @Test
  void findReservation() {
  }

  @Test
  void testConfirmReservation() {
  }

  @Test
  void testCancelReservation() {
  }

  @Test
  void testRejectReservation() {
  }

  @Test
  void testAllocateReservation() {
  }

  @Test
  void startReservation() {
  }

  @Test
  void finishReservation() {
  }

  @Test
  void createReservationInvoice() {
  }

  @Test
  void payReservation() {
  }
}
