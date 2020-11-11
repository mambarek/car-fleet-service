package com.it2go.micro.carfleetservice.persistence.jpa.entities;

import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by mmbarek on 09.11.2020.
 */
@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CAR_RESERVATION")
public class CarReservationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_res_seq_gen")
  @SequenceGenerator(name = "car_res_seq_gen", sequenceName = "car_res_seq", allocationSize = 50)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "PUBLIC_ID", unique = true, nullable = false)
  private UUID publicId;

  @JoinColumn(referencedColumnName = "PUBLIC_ID", name = "CAR_PUBLIC_ID")
  @OneToOne(fetch = FetchType.LAZY)
  private CarEntity car;

  @JoinColumn(referencedColumnName = "PUBLIC_ID", name = "DRIVER_PUBLIC_ID")
  @OneToOne(fetch = FetchType.LAZY)
  private DriverEntity driver;

  @Basic
  @Column(name = "CREATED_AT", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private OffsetDateTime reservedFrom;

  @Basic
  @Column(name = "UPDATED_AT", columnDefinition = "TIMESTAMP WITH TIME ZONE")
  private OffsetDateTime reservedTo;

  @Basic
  @Column(name = "STATUS", length = 20)
  private String status;
}
