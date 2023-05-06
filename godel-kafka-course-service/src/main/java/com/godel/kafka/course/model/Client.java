package com.godel.kafka.course.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
@Entity
public class Client {

  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID clientId;

  @NonNull
  @Column(name = "email", nullable = false)
  private String email;

}
