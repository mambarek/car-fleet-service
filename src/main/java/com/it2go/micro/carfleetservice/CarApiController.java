package com.it2go.micro.carfleetservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3")
public class CarApiController implements CarsApi {

}
