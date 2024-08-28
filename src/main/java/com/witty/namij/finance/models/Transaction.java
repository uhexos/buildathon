package com.witty.namij.finance.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  // Getters and Setters
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String sender;
  private String receiver;
  private String accountNumber;
  private String transactionType;
  private BigDecimal amount;
  private String currency;
  private LocalDateTime dateTime;
  private String description = "";
  private BigDecimal availableBalance;
  private BigDecimal currentBalance;
  private String location;
}
