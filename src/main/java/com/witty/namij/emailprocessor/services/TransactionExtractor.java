package com.witty.namij.emailprocessor.services;

import com.witty.namij.emailprocessor.models.Message;
import com.witty.namij.finance.models.Transaction;

public abstract class TransactionExtractor {

    public abstract Transaction extract(Message message);
    public abstract boolean canHandle(Message message);

    public String extractField(String notification, String fieldName) {
        String[] lines = notification.split("\\n");
        for (String line : lines) {
            if (line.contains(fieldName)) {
                return line.split(";;")[1].trim();
            }
        }
        return "";
    }
}