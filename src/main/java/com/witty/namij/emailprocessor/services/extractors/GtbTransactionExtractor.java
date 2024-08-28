package com.witty.namij.emailprocessor.services.extractors;

import com.witty.namij.emailprocessor.models.Message;
import com.witty.namij.emailprocessor.services.TransactionExtractor;
import com.witty.namij.finance.models.Transaction;
import org.springframework.stereotype.Component;

@Component
public class GtbTransactionExtractor extends TransactionExtractor {

    @Override
    public Transaction extract(Message message) {
        // Extraction logic (as before)
        return  null;
    }

    @Override
    public boolean canHandle(Message message) {
        return false ;
    }
}

