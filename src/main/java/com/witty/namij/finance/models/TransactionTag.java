package com.witty.namij.finance.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "transaction_tags", schema = "libremail")
public class TransactionTag {
  @EmbeddedId private TransactionTagId id;

  // TODO [Reverse Engineering] generate columns from DB
}
