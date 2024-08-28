package com.witty.namij.emailprocessor.controllers;

import com.witty.namij.emailprocessor.models.Message;
import com.witty.namij.emailprocessor.repos.MessageRepository;
import com.witty.namij.emailprocessor.services.TransactionParserService;
import java.util.List;

import com.witty.namij.finance.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

  @Autowired private MessageRepository messageRepository;
  @Autowired private TransactionParserService transactionParserService;

  // Get all messages
  @GetMapping
  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }
    @GetMapping("sim")
    public List<Transaction> sim() {
        return messageRepository.findAll().stream()
                .map(m -> transactionParserService.parseTransaction(m)).toList();
    }

}
