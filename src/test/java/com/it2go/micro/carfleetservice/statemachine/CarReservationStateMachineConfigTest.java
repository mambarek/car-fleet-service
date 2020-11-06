package com.it2go.micro.carfleetservice.statemachine;


import com.it2go.micro.carfleetservice.generated.domain.CarReservationEventEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * created by mmbarek on 06.11.2020.
 */
@SpringBootTest
class CarReservationStateMachineConfigTest {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  StateMachineFactory<CarReservationStatusEnum, CarReservationEventEnum> factory;

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

    stateMachine.sendEvent(CarReservationEventEnum.CONFIRM_RESERVATION);
    System.out.println(stateMachine.getState().getId());
   // assert(stateMachine.getState().getId()).equals(CarReservationStatusEnum.CONFIRMED);
    // use assertj
    assertThat(stateMachine.getState().getId()).isEqualTo(CarReservationStatusEnum.CONFIRMED);

  }

}
