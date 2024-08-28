package com.witty.namij.finance.controllers;

import com.witty.namij.emailprocessor.repos.MessageRepository;
import java.util.List;

import com.witty.namij.finance.models.Transaction;
import com.witty.namij.finance.models.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    // Get all messages
    @GetMapping
    public List<Transaction> getAllMessages() {
        return transactionRepository.findAll();
    }

}
