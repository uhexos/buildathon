package com.witty.namij.finance.models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "entity_category_suggestions", schema = "libremail")
public class EntityCategorySuggestion {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "entity_id", nullable = false)
  private Integer entityId;

  @Column(name = "category_id", nullable = false)
  private Integer categoryId;

  @ColumnDefault("1")
  @Column(name = "suggestion_count")
  private Integer suggestionCount;
}
