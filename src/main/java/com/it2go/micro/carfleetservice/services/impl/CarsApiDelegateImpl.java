package com.it2go.micro.carfleetservice.services.impl;

import com.it2go.micro.carfleetservice.generated.controller.ApiApiDelegate;
import com.it2go.micro.carfleetservice.generated.domain.Car;
import com.it2go.micro.carfleetservice.generated.domain.Car.StatusEnum;
import com.it2go.micro.carfleetservice.generated.domain.CarSearchResult;
import com.it2go.micro.carfleetservice.generated.domain.CarTableItem;
import com.it2go.micro.carfleetservice.generated.domain.SearchTemplate;
import com.it2go.micro.carfleetservice.mapper.SearchTemplateMapper;
import com.it2go.micro.carfleetservice.services.CarSearchService;
import com.it2go.micro.carfleetservice.services.CarService;
import com.it2go.util.jpa.search.SearchResult;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@Component
@AllArgsConstructor
public class CarsApiDelegateImpl implements ApiApiDelegate {
  
  private final CarService carService;
  private final CarSearchService carSearchService;

  private final SearchTemplateMapper searchTemplateMapper;

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  @Override
  public ResponseEntity<Long> getCarsCount() {
    log.info("-- getCarsCount()");
    var count = carService.countCars();
    log.info("Count of cars found is: " + count);
    return ResponseEntity.ok(count);
  }

  @Override
  public ResponseEntity<List<Car>> getCars() {
    log.info("-- getCars()");
    return ResponseEntity.ok(carService.findAllCars());
  }

  @Override
  public ResponseEntity<CarSearchResult> search(SearchTemplate searchTemplate) {
    log.info("search() called");
    log.debug("SearchTemplate:["+ searchTemplate +"]");
    //todo mapper to be refactored use only generated template perhaps!
    com.it2go.util.jpa.search.SearchTemplate template = searchTemplateMapper.map(searchTemplate);
/*    List<CarTableItem> carTableItems = carSearchService.filterCars(template);

    CarSearchResult searchResult = new CarSearchResult();
    searchResult.setRows(carTableItems);*/

    CarSearchResult carSearchResult = carSearchService.searchCars(template);
    return ResponseEntity.ok(carSearchResult);
  }

  @Override
  public ResponseEntity<Void> deleteCar(UUID publicId) {
    log.info(String.format("-- deleteCar() with publicId: [%s]", publicId));
    carService.deleteCar(publicId);
    log.info(String.format("-- deleteCar() with publicId: [%s] successfully deleted", publicId));
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Car> findCarByPublicId(UUID publicId) {
    log.info(String.format("-- findCarByPublicId() with publicId: [%s]", publicId));
    Car carByPublicId = carService.findCarByPublicId(publicId);
    if (carByPublicId == null) {
      return ResponseEntity.notFound().build();
    }
    log.info(
        String.format("-- findCarByPublicId() with publicId: [%s] successfully fetched", publicId));
    return ResponseEntity.ok(carByPublicId);
  }

  @Override
  public ResponseEntity<Car> updateCar(UUID publicId, Car car) {
    log.info(String.format("-- updateCar with publicId: [%s] and publicId param [%s]", publicId,
        car.getPublicId()));
    if (!publicId.equals(car.getPublicId())) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    Car updateCar = carService.updateCar(car);
    log.info(String.format("-- updateCar [%s] successfully updated", publicId));
    return ResponseEntity.ok(updateCar);
  }

  @Override
  public ResponseEntity<Car> createCar(Car car) {
    car.setPublicId(UUID.randomUUID());
    car.setStatus(StatusEnum.READY);
    log.info(String.format("-- createCar publicId: [%s]", car.getPublicId()));
    Car savedCar = carService.createCar(car);
    log.info(String.format("-- updateCar [%s] successfully created", car.getPublicId()));
    // create location url and set it in header
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{publicId}")
        .buildAndExpand(savedCar.getPublicId()).toUri();
    log.info("-- Location: " + uri);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);

    return new ResponseEntity<>(savedCar, responseHeaders, HttpStatus.CREATED);
  }
}
