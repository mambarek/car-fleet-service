package com.it2go.micro.carfleetservice.services.impl;

import com.it2go.micro.carfleetservice.generated.domain.CarSearchResult;
import com.it2go.micro.carfleetservice.generated.domain.CarTableItem;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity_;
import com.it2go.micro.carfleetservice.services.CarSearchService;
import com.it2go.util.jpa.search.PredicateBuilder;
import com.it2go.util.jpa.search.SearchOrder;
import com.it2go.util.jpa.search.SearchTemplate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarSearchServiceImpl implements CarSearchService {

  final EntityManager entityManager;

  @Override
  public CarSearchResult searchCars(SearchTemplate searchTemplate) {
    CarSearchResult searchResult = new CarSearchResult();
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<CarTableItem> criteriaQuery = cb.createQuery(CarTableItem.class);
    Root<CarEntity> carEntityRoot = criteriaQuery.from(CarEntity.class);

    final CompoundSelection<CarTableItem> compoundSelection = cb
        .construct(CarTableItem.class,
            carEntityRoot.get(CarEntity_.publicId),
            carEntityRoot.get(CarEntity_.brand),
            carEntityRoot.get(CarEntity_.model),
            carEntityRoot.get(CarEntity_.description),
            carEntityRoot.get(CarEntity_.manufacturingDate),
            carEntityRoot.get(CarEntity_.color),
            carEntityRoot.get(CarEntity_.mileage),
            carEntityRoot.get(CarEntity_.status));

    final CriteriaQuery<CarTableItem> select = criteriaQuery.select(compoundSelection);

    PredicateBuilder predicateBuilder = null;
    if (searchTemplate.getFilters() != null) {
      predicateBuilder = PredicateBuilder
          .createPredicates(cb, carEntityRoot, searchTemplate.getFilters());

      select.where(predicateBuilder.getPredicates().toArray(new Predicate[0]));
    }

    if (searchTemplate.getOrderBy() != null && !searchTemplate.getOrderBy().isEmpty()) {
      SearchOrder orderDirection = searchTemplate.getOrderDirection();
      if (orderDirection == null) {
        orderDirection = SearchOrder.ASC;
      }
      Order orderBy = null;
      switch (orderDirection) {
        case ASC:
          orderBy = cb.asc(carEntityRoot.get(searchTemplate.getOrderBy()));
          break;
        case DESC:
          orderBy = cb.desc(carEntityRoot.get(searchTemplate.getOrderBy()));
          break;
      }
      select.orderBy(orderBy);
    }

    final TypedQuery<CarTableItem> query = entityManager.createQuery(select);

    // set query parameter if exists
    if (predicateBuilder != null) {
      predicateBuilder.getParamMap().forEach(query::setParameter);
    }

    if (searchTemplate.getMaxResult() > 0) {
      query.setMaxResults(searchTemplate.getMaxResult());
    }

    query.setFirstResult(searchTemplate.getOffset());

    List<CarTableItem> carTableItems = query.getResultList();

    searchResult.setRows(carTableItems);

    if (predicateBuilder != null) {
      searchResult.setRecords(this.countCars(searchTemplate, predicateBuilder));
    }

    return searchResult;
  }

  public Long countCars(SearchTemplate searchTemplate, PredicateBuilder predicateBuilder) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
    Root<CarEntity> carEntityRoot = criteriaQuery.from(CarEntity.class);

    final CriteriaQuery<Long> count = criteriaQuery.select(cb.count(carEntityRoot));
    count.where(predicateBuilder.getPredicates().toArray(new Predicate[0]));

    final TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);

    // set query parameter if exists
    predicateBuilder.getParamMap().forEach(query::setParameter);

    final Long countResult = query.getSingleResult();

    return countResult;
  }
}
