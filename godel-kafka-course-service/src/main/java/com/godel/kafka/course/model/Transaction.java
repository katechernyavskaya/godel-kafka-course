package com.godel.kafka.course.model;

import java.time.LocalDateTime;
import java.util.UUID;


public class Transaction {

  private UUID transactionId;
  private UUID clientId;
  private String bank;
  private OrderType orderType;
  private Integer quantity;
  private Double price;
  private LocalDateTime createdAt;


}
