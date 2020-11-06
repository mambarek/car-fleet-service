package com.it2go.micro.carfleetservice.persistence.jpa.entities;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
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

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CAR")
public class CarEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_seq_gen")
  @SequenceGenerator(name = "car_seq_gen", sequenceName = "car_seq", allocationSize = 50)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "PUBLIC_ID", unique = true, nullable = false)
  private UUID publicId;

  @Basic
  @Column(name = "BRAND", nullable = false, length = 100)
  private String brand;

  @Basic
  @Column(name = "MODEL", nullable = false, length = 100)
  private String model;

  @Basic
  @Column(name = "ENGINE_TYPE", nullable = false, length = 10)
  private String engineType;

  @Basic
  @Column(name = "FUEL_TYPE", nullable = true, length = 10)
  private String fuelType;

  @Basic
  @Column(name = "DESCRIPTION", nullable = true, length = 500)
  private String description;

  @Basic
  @Column(name = "MF_DATE", nullable = false, columnDefinition = "DATE")
  private LocalDate manufacturingDate;

  @Basic
  @Column(name = "COLOR", nullable = false, length = 50)
  private String color;

  @Basic
  @Column(name = "MILEAGE")
  private Integer mileage;

  @Basic
  @Column(name = "STATUS", length = 20)
  private String status;
}
