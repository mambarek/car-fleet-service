package com.it2go.micro.carfleetservice.persistence.jpa.repositories;

import com.it2go.micro.carfleetservice.generated.domain.CarReservation;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarEntity;
import com.it2go.micro.carfleetservice.persistence.jpa.entities.CarReservationEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * created by mmbarek on 09.11.2020.
 */
public interface CarReservationRepository extends JpaRepository<CarReservationEntity, Long> {
  CarReservationEntity findByPublicId(UUID publicId);
}
