package com.it2go.micro.carfleetservice.statemachine;

import com.it2go.micro.carfleetservice.generated.domain.CarReservationEventEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import java.util.EnumSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

/**
 * created by mmbarek on 06.11.2020.
 */
@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableStateMachineFactory
public class CarReservationStateMachineConfig extends
    StateMachineConfigurerAdapter<CarReservationStatusEnum, CarReservationEventEnum> {

  private final AllocateReservation allocateReservation;
  private final ReservationGuard reservationGuard;

  @Override
  public void configure(StateMachineStateConfigurer<CarReservationStatusEnum, CarReservationEventEnum> states) throws Exception {
      states.withStates()
          .initial(CarReservationStatusEnum.NEW)
          .states(EnumSet.allOf(CarReservationStatusEnum.class))
          .end(CarReservationStatusEnum.REJECTED)
          .end(CarReservationStatusEnum.CANCELLED)
          .end(CarReservationStatusEnum.PAY_EXCEPTION)
          .end(CarReservationStatusEnum.PAYED);
    }

  @Override
  public void configure(StateMachineTransitionConfigurer<CarReservationStatusEnum, CarReservationEventEnum> transitions)
      throws Exception {
    transitions.withExternal()
        .source(CarReservationStatusEnum.NEW)
        .target(CarReservationStatusEnum.CONFIRMED)
        .event(CarReservationEventEnum.CONFIRM_RESERVATION)
        .action(allocateReservation)
       // .guard(reservationGuard)
        .and().withExternal()
        .source(CarReservationStatusEnum.NEW)
        .target(CarReservationStatusEnum.REJECTED)
        .event(CarReservationEventEnum.REJECT_RESERVATION)
        .and().withExternal()
        .source(CarReservationStatusEnum.NEW)
        .target(CarReservationStatusEnum.CANCELLED)
        .event(CarReservationEventEnum.CANCEL_RESERVATION)
        .and().withExternal()
        .source(CarReservationStatusEnum.CONFIRMED)
        .target(CarReservationStatusEnum.CANCELLED)
        .event(CarReservationEventEnum.CANCEL_RESERVATION)
        .and().withExternal()
        .source(CarReservationStatusEnum.CONFIRMED)
        .target(CarReservationStatusEnum.ALLOCATED)
        .event(CarReservationEventEnum.ALLOCATE_RESERVATION)
        .and().withExternal()
        .source(CarReservationStatusEnum.ALLOCATED)
        .target(CarReservationStatusEnum.CANCELLED)
        .event(CarReservationEventEnum.CANCEL_RESERVATION)
        .and().withExternal()
        .source(CarReservationStatusEnum.ALLOCATED)
        .target(CarReservationStatusEnum.STARTED)
        .event(CarReservationEventEnum.START_RESERVATION)
        .and().withExternal()
        .source(CarReservationStatusEnum.STARTED)
        .target(CarReservationStatusEnum.FINISHED)
        .event(CarReservationEventEnum.FINISH_RESERVATION)
        .and().withExternal()
        .source(CarReservationStatusEnum.FINISHED)
        .target(CarReservationStatusEnum.WAITING_FOR_PAY)
        .event(CarReservationEventEnum.CREATE_INVOICE)
        .and().withExternal()
        .source(CarReservationStatusEnum.WAITING_FOR_PAY)
        .target(CarReservationStatusEnum.PAY_EXCEPTION)
        .event(CarReservationEventEnum.PAY_INVOICE)
        .and().withExternal()
        .source(CarReservationStatusEnum.WAITING_FOR_PAY)
        .target(CarReservationStatusEnum.PAYED)
        .event(CarReservationEventEnum.PAY_INVOICE);
  }

  @Override
  public void configure(
      StateMachineConfigurationConfigurer<CarReservationStatusEnum, CarReservationEventEnum> config)
      throws Exception {

    StateMachineListener<CarReservationStatusEnum, CarReservationEventEnum> adapter = new StateMachineListenerAdapter<>() {
      @Override
      public void stateChanged(State<CarReservationStatusEnum, CarReservationEventEnum> from,
          State<CarReservationStatusEnum, CarReservationEventEnum> to) {
        if(from == null) {
          log.info(String.format("Initial state %s", to.getId()));
        }
        else {
          log.info(String.format("stateChanged(from: %s, to: %s)", from.getId(), to.getId()));
        }
      }
    };

    config.withConfiguration()
        .listener(adapter);
  }

}
