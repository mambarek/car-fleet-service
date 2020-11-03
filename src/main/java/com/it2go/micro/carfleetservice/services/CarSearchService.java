package com.it2go.micro.carfleetservice.services;

import com.it2go.micro.carfleetservice.generated.domain.CarSearchResult;
import com.it2go.micro.carfleetservice.generated.domain.CarTableItem;
import com.it2go.util.jpa.search.SearchTemplate;
import java.util.List;

public interface CarSearchService {

  public CarSearchResult searchCars(SearchTemplate searchTemplate);
}
