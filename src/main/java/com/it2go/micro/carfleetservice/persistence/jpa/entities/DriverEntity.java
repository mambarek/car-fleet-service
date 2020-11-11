package com.it2go.micro.carfleetservice.persistence.jpa.entities;

import com.it2go.util.jpa.entities.PersonData;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.it2go.util.jpa.entities.AddressEntity;

/**
 * created by mmbarek on 10.11.2020.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DRIVER")
public class DriverEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_seq_gen")
  @SequenceGenerator(name = "driver_seq_gen", sequenceName = "driver_seq", allocationSize = 50)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "PUBLIC_ID", unique = true, nullable = false)
  private UUID publicId;

  @Embedded
  private PersonData data;
}
