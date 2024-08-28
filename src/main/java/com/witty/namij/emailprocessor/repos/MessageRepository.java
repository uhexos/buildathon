package com.witty.namij.emailprocessor.repos;

import com.witty.namij.emailprocessor.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {}
