package com.witty.namij.emailprocessor.services;

import com.witty.namij.emailprocessor.models.Message;
import com.witty.namij.emailprocessor.repos.MessageRepository;
import com.witty.namij.finance.models.Transaction;
import com.witty.namij.finance.models.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class TransactionParserService {

    private final Map<String, TransactionExtractor> extractors;
    private final MessageRepository messageRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionParserService(Map<String, TransactionExtractor> extractors,
                                    MessageRepository messageRepository, TransactionRepository transactionRepository) {
        this.extractors = extractors;
        this.messageRepository = messageRepository;
        this.transactionRepository = transactionRepository;
    }

    public Transaction parseTransaction(Message message) {
        return extractors.values().stream()
                .filter(extractor -> extractor.canHandle(message))
                .findFirst()
                .map(extractor -> extractor.extract(message))
                .orElseThrow(() -> new IllegalArgumentException("No suitable extractor found for the given notification"));
    }

    @Scheduled(fixedRate = 50000)
    public void importTransactionsFromMessages() {
        messageRepository.findAll().forEach(message -> {
          Optional<Transaction> existingTransaction = transactionRepository.findById(message.getUid().longValue());
          if (existingTransaction.isEmpty()) {
              transactionRepository.save(parseTransaction(message));
          }
        });
    }
}