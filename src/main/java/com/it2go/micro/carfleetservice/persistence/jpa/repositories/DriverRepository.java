package com.it2go.micro.carfleetservice.persistence.jpa.repositories;

import com.it2go.micro.carfleetservice.persistence.jpa.entities.DriverEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * created by mmbarek on 10.11.2020.
 */
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

  DriverEntity findByPublicId(UUID publicId);
}
