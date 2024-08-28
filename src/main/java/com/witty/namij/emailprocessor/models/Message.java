package com.witty.namij.emailprocessor.models;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "emails", schema = "nami")
public class Message {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "uid", nullable = false)
  private Integer uid;

  @Column(name = "message_id", nullable = false)
  private String messageId;

  @Lob
  @Column(name = "subject")
  private String subject;

  @Lob
  @Column(name = "`from`")
  private String from;

  @Lob
  @Column(name = "`to`")
  private String to;

  @Column(name = "date")
  private Instant date;

  @Column(name = "body_structure")
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> bodyStructure;

  @Lob
  @Column(name = "body")
  private String body;

  @Lob
  @Column(name = "text")
  private String text;

  @Lob
  @Column(name = "html")
  private String html;

  @ColumnDefault("CURRENT_TIMESTAMP")
  @Column(name = "created_at")
  private Instant createdAt;
}
