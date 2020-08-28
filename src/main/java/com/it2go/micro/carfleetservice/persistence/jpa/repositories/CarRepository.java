package com.it2go.micro.carfleetservice.persistence.jpa.repositories;

import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends CrudRepository<CarEntity, Long> {
  Optional<CarEntity> findByPublicId(UUID publicId);
}
