package com.it2go.micro.carfleetservice.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.it2go.micro.carfleetservice.generated.domain.CarTableItem;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/**
 * created by mmbarek on 05.12.2020.
 */
public class JacksonTests {

  ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void testDateFormatISO(){

    CarTableItem carTableItem = new CarTableItem();
    carTableItem.brand("BMW").model("X5").manufacturingDate(LocalDate.parse("1970-01-06"));

    System.out.println(carTableItem);
  }
}
