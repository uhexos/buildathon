package com.witty.namij;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "email")
@Component
public class EmailProperties {

  private String username;
  private String password;
  private String host;
  private String port;
  private String mailbox;
  private String scheme = "imap";
  private long pollRate = 30000;

  public String getImapUrl() {
    return String.format("%s://%s:%s@%s:%s/%s",this.scheme, this.username, this.password, this.host, this.port, this.mailbox);
  }
}

