package com.it2go.micro.carfleetservice.persistence.jpa.entities;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
  @GeneratedValue
  private Long id;

  @Column(name = "PUBLIC_ID", unique = true, nullable = false)
  private UUID publicId;

  @Basic
  @Column(name = "NAME", nullable = false, length = 100)
  private String name;

  @Basic
  @Column(name = "DESCRIPTION", nullable = false, length = 500)
  private String description;

  @Basic
  @Column(name = "MILEAGE")
  private Double mileage;

  @Basic
  @Column(name = "RESERVED_FROM", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private OffsetDateTime reservedFrom;

  @Basic
  @Column(name = "RESERVED_TO", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private OffsetDateTime reservedTo;

  @Basic
  @Column(name = "STATUS", length = 20)
  private String status;
}
