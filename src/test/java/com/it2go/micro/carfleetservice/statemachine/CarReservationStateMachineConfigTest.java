package com.it2go.micro.carfleetservice.statemachine;


import com.it2go.micro.carfleetservice.generated.domain.CarReservationEventEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.services.impl.CarReservationServiceImpl;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by mmbarek on 06.11.2020.
 */
//@Disabled
@SpringBootTest
class CarReservationStateMachineConfigTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  StateMachineFactory<CarReservationStatusEnum, CarReservationEventEnum> factory;

  private void sendEvent(UUID publicId, StateMachine<CarReservationStatusEnum, CarReservationEventEnum> sm, CarReservationEventEnum event) {

    Message<CarReservationEventEnum> msg = MessageBuilder
        .withPayload(event)
        .setHeader(CarReservationServiceImpl.RESERVATION_ID_HEADER, publicId)
        .build();

    sm.sendEvent(msg);
  }

  @Test
  void testStateMachine(){
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = factory
        .getStateMachine();

    stateMachine.start();
    System.out.println(stateMachine.getState().getId());
    assert(stateMachine.getState().getId()).equals(CarReservationStatusEnum.NEW);

    stateMachine.sendEvent(CarReservationEventEnum.REJECT_RESERVATION);
    System.out.println(stateMachine.getState().getId());
    assert(stateMachine.getState().getId()).equals(CarReservationStatusEnum.REJECTED);

    // send wrong event
    stateMachine.sendEvent(CarReservationEventEnum.START_RESERVATION);
    System.out.println(stateMachine.getState().getId());
    assert(stateMachine.getState().getId()).equals(CarReservationStatusEnum.REJECTED);

    stateMachine.stop();
    stateMachine.start();
    System.out.println(stateMachine.getState().getId());
    //stateMachine.sendEvent(CarReservationEventEnum.CONFIRM_RESERVATION);
    sendEvent(UUID.randomUUID(), stateMachine, CarReservationEventEnum.CONFIRM_RESERVATION);
    System.out.println(stateMachine.getState().getId());
   // assert(stateMachine.getState().getId()).equals(CarReservationStatusEnum.CONFIRMED);
    // use assertj
    assertThat(stateMachine.getState().getId()).isEqualTo(CarReservationStatusEnum.CONFIRMED);
  }

  @Test
  void testRehydrateMachine(){
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = factory
        .getStateMachine();
    // init the statemachine from DB. The state STARTED may come from a Reservation instance
    // 1. stop the machine
    stateMachine.stop();
    // 2. set the state explicitly
    stateMachine.getStateMachineAccessor().doWithAllRegions(f -> {
      f.resetStateMachine(new DefaultStateMachineContext<>(CarReservationStatusEnum.STARTED, null, null, null));
    });
    // 3. start the machine again
    stateMachine.start();
    System.out.println(stateMachine.getState().getId());
    assertThat(stateMachine.getState().getId()).isEqualTo(CarReservationStatusEnum.STARTED);
  }

  @Test
  void testSendMessageToMachine(){
    StateMachine<CarReservationStatusEnum, CarReservationEventEnum> stateMachine = factory
        .getStateMachine();

    Message<CarReservationEventEnum> msg = MessageBuilder
        .withPayload(CarReservationEventEnum.CONFIRM_RESERVATION)
        .setHeader("ALLOCATION", "1234567890")
        .build();

    stateMachine.start();
    stateMachine.sendEvent(msg);
    System.out.println(stateMachine.getState().getId());
  }

}
