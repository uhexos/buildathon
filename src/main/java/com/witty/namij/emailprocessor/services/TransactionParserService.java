package com.witty.namij.emailprocessor.services;

import com.witty.namij.emailprocessor.models.Message;
import com.witty.namij.finance.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TransactionParserService {

    private final Map<String, TransactionExtractor> extractors;

    @Autowired
    public TransactionParserService(Map<String, TransactionExtractor> extractors) {
        this.extractors = extractors;
    }

    public Transaction parseTransaction(Message message) {
        return extractors.values().stream()
                .filter(extractor -> extractor.canHandle(message))
                .findFirst()
                .map(extractor -> extractor.extract(message))
                .orElseThrow(() -> new IllegalArgumentException("No suitable extractor found for the given notification"));
    }
}