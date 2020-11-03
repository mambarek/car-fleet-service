package com.it2go.micro.carfleetservice.services.impl;

import com.it2go.micro.carfleetservice.generated.controller.ApiApiDelegate;
import com.it2go.micro.carfleetservice.generated.domain.Car;
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
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
@AllArgsConstructor
public class CarsApiDelegateImpl implements ApiApiDelegate {

  private static final Logger LOG = LoggerFactory.getLogger(CarsApiDelegateImpl.class);

  private final CarService carService;
  private final CarSearchService carSearchService;

  private final SearchTemplateMapper searchTemplateMapper;

  @Override
  public Optional<NativeWebRequest> getRequest() {
    return Optional.empty();
  }

  @Override
  public ResponseEntity<List<Car>> getCars() {
    LOG.info("-- getCars()");
    return ResponseEntity.ok(carService.findAllCars());
  }

  @Override
  public ResponseEntity<CarSearchResult> search(SearchTemplate searchTemplate) {
    LOG.info("search() called");
    LOG.debug("SearchTemplate:["+ searchTemplate +"]");
    //todo mapper tobe refactored use only generated template perhaps!
    com.it2go.util.jpa.search.SearchTemplate template = searchTemplateMapper.map(searchTemplate);
    List<CarTableItem> carTableItems = carSearchService.filterCars(template);

    CarSearchResult searchResult = new CarSearchResult();
    searchResult.setRows(carTableItems);

    return ResponseEntity.ok(searchResult);
  }

  @Override
  public ResponseEntity<Void> deleteCar(UUID publicId) {
    LOG.info(String.format("-- deleteCar() with publicId: [%s]", publicId));
    carService.deleteCar(publicId);
    LOG.info(String.format("-- deleteCar() with publicId: [%s] successfully deleted", publicId));
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<Car> getCarByPublicId(UUID publicId) {
    LOG.info(String.format("-- getCarByPublicId() with publicId: [%s]", publicId));
    Car carByPublicId = carService.findCarByPublicId(publicId);
    if (carByPublicId == null) {
      return ResponseEntity.notFound().build();
    }
    LOG.info(
        String.format("-- getCarByPublicId() with publicId: [%s] successfully fetched", publicId));
    return ResponseEntity.ok(carByPublicId);
  }

  @Override
  public ResponseEntity<Car> updateCar(UUID publicId, Car car) {
    LOG.info(String.format("-- updateCar with publicId: [%s] and publicId param [%s]", publicId,
        car.getPublicId()));
    if (!publicId.equals(car.getPublicId())) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    Car updateCar = carService.updateCar(car);
    LOG.info(String.format("-- updateCar [%s] successfully updated", publicId));
    return ResponseEntity.ok(updateCar);
  }

  @Override
  public ResponseEntity<Car> createCar(Car car) {
    LOG.info(String.format("-- createCar publicId: [%s]", car.getPublicId()));
    Car savedCar = carService.createCar(car);
    LOG.info(String.format("-- updateCar [%s] successfully created", car.getPublicId()));
    // create location url and set it in header
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{publicId}")
        .buildAndExpand(savedCar.getPublicId()).toUri();
    LOG.info("-- Location: " + uri);
    HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.setLocation(uri);

    return new ResponseEntity<>(savedCar, responseHeaders, HttpStatus.CREATED);
  }
}
