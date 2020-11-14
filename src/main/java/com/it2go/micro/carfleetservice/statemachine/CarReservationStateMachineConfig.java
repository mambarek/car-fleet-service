package com.it2go.micro.carfleetservice.statemachine;

import com.it2go.micro.carfleetservice.generated.domain.CarReservationEventEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import com.it2go.micro.carfleetservice.services.impl.CarReservationServiceImpl;
import java.text.Format;
import java.util.EnumSet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigBuilder;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.configuration.StateMachineConfiguration;
import org.springframework.statemachine.guard.Guard;
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

  @Override
  public void configure(
      StateMachineStateConfigurer<CarReservationStatusEnum, CarReservationEventEnum> states)
      throws Exception {
    states.withStates()
        .initial(CarReservationStatusEnum.NEW)
        .states(EnumSet.allOf(CarReservationStatusEnum.class))
        .end(CarReservationStatusEnum.REJECTED)
        .end(CarReservationStatusEnum.CANCELLED)
        .end(CarReservationStatusEnum.PAY_EXCEPTION)
        .end(CarReservationStatusEnum.PAYED);
  }

  @Override
  public void configure(
      StateMachineTransitionConfigurer<CarReservationStatusEnum, CarReservationEventEnum> transitions)
      throws Exception {
    transitions.withExternal()
        .source(CarReservationStatusEnum.NEW)
        .target(CarReservationStatusEnum.CONFIRMED)
        .event(CarReservationEventEnum.CONFIRM_RESERVATION)
        .action(testAction())
        .guard(reservationIdGuard())
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
        log.info(String.format("stateChanged(from: %s, to: %s)", from.getId(), to.getId()));
      }
    };

    config.withConfiguration()
        .listener(adapter);
  }

  /**
   * Guards und Actions kann man in eigene Klasssen auslagern s. sfg-beer-service from John Thomson
   * @return
   */
  public Guard<CarReservationStatusEnum, CarReservationEventEnum> reservationIdGuard(){
    return stateContext -> {
      return stateContext.getMessageHeaders().containsKey(CarReservationServiceImpl.RESERVATION_ID_HEADER);
    };
  }

  /**
   * Action werden genutzt um logic bei bestimmte Statusänderungen zu triggern
   * Interceptor wir benutzt um status in der DB zu speichern
   * @return
   */
  public Action<CarReservationStatusEnum, CarReservationEventEnum> testAction(){
    return stateContext -> {
      System.out.println(">>> testAction was called!!!");
      System.out.println(stateContext.getStateMachine().getState().getId());
      CarReservationEntity reservationEntity = stateContext.getExtendedState()
          .get(CarReservationServiceImpl.RESERVATION_ID_HEADER, CarReservationEntity.class);

      System.out.println("-- Reservation status: " + reservationEntity.getStatus());
    };
  }
}
