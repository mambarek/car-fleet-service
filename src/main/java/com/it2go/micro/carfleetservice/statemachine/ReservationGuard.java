package com.it2go.micro.carfleetservice.statemachine;

import com.it2go.micro.carfleetservice.generated.domain.CarReservationEventEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarReservationStatusEnum;
import com.it2go.micro.carfleetservice.services.impl.CarReservationServiceImpl;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

/**
 * created by mmbarek on 07.03.2021.
 */
@Component
public class ReservationGuard implements Guard<CarReservationStatusEnum, CarReservationEventEnum> {

  @Override
  public boolean evaluate(
      StateContext<CarReservationStatusEnum, CarReservationEventEnum> stateContext) {
    return stateContext.getMessageHeaders()
        .containsKey(CarReservationServiceImpl.RESERVATION_ID_HEADER);
  }
}
