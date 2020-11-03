package com.it2go.micro.carfleetservice.services.impl;

import com.it2go.micro.carfleetservice.generated.domain.CarTableItem;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity_;
import com.it2go.micro.carfleetservice.services.CarSearchService;
import com.it2go.util.jpa.search.PredicateBuilder;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarSearchServiceImpl implements CarSearchService {

  final EntityManager entityManager;

  @Override
  public List<CarTableItem> filterCars(SearchTemplate searchTemplate) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<CarTableItem> criteriaQuery = cb.createQuery(CarTableItem.class);
    Root<CarEntity> projectEntityRoot = criteriaQuery.from(CarEntity.class);

    final CompoundSelection<CarTableItem> compoundSelection = cb
        .construct(CarTableItem.class,
            projectEntityRoot.get(CarEntity_.publicId),
            projectEntityRoot.get(CarEntity_.brand),
            projectEntityRoot.get(CarEntity_.model),
            projectEntityRoot.get(CarEntity_.description),
            projectEntityRoot.get(CarEntity_.manufacturingDate),
            projectEntityRoot.get(CarEntity_.color),
            projectEntityRoot.get(CarEntity_.mileage),
            projectEntityRoot.get(CarEntity_.status));

    final CriteriaQuery<CarTableItem> select = criteriaQuery.select(compoundSelection);

    PredicateBuilder predicateBuilder = null;
    if (searchTemplate.getFilters() != null) {
      predicateBuilder = PredicateBuilder
          .createPredicates(cb, projectEntityRoot, searchTemplate.getFilters());

      select.where(predicateBuilder.getPredicates().toArray(new Predicate[0]));
    }

    // Order by
    Order orderBy = null;

    if (searchTemplate.getOrderBy() != null && !searchTemplate.getOrderBy()
        .isEmpty()) {
      switch (searchTemplate.getOrderDirection()) {
        case ASC:
          orderBy = cb.asc(projectEntityRoot.get(searchTemplate.getOrderBy()));
          break;
        case DESC:
          orderBy = cb.desc(projectEntityRoot.get(searchTemplate.getOrderBy()));
          break;
      }
    }

    if (orderBy != null) {
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

    return query.getResultList();
  }

}
