package com.it2go.micro.carfleetservice.persistence.jpa.repositories;

import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface CarRepository extends JpaRepository<CarEntity, Long> {
  Optional<CarEntity> findByPublicId(UUID publicId);
}
