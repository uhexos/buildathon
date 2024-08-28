package com.witty.namij.finance.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

@Getter
@Setter
@Embeddable
public class TransactionTagId implements Serializable {
  private static final long serialVersionUID = -3921399277970373685L;

  @Column(name = "transaction_id", nullable = false)
  private Integer transactionId;

  @Column(name = "tag_id", nullable = false)
  private Integer tagId;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    TransactionTagId entity = (TransactionTagId) o;
    return Objects.equals(this.tagId, entity.tagId)
        && Objects.equals(this.transactionId, entity.transactionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tagId, transactionId);
  }
}
