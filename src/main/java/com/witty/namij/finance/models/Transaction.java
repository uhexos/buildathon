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

@Entity
@Data
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
//  TODO change from category to comment which will then use "AI" to classify
//  TODO "AI" or user will set the actual category remember to mark the ones  ain fill as suggested by AI
  private String category;

  public void setCategory(String category) {
    if (category != null && !category.isEmpty()) {
      this.category = category.toUpperCase();
    } else {
      this.category = category;
    }
  }
}
