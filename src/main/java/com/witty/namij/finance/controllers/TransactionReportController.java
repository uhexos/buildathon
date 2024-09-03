package com.witty.namij.finance.controllers;

import com.witty.namij.finance.TransactionReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
public class TransactionReportController {

    @Autowired
    private TransactionReportService reportService;

    @GetMapping("/volume-over-time")
    public List<Map<String, Object>> getTransactionVolumeOverTime() {
        return reportService.getTransactionVolumeOverTime();
    }

    @GetMapping("/total-amount-over-time")
    public List<Map<String, Object>> getTotalAmountTransactedOverTime() {
        return reportService.getTotalAmountTransactedOverTime();
    }

    @GetMapping("/top-transaction-types")
    public List<Map<String, Object>> getTopTransactionTypes() {
        return reportService.getTopTransactionTypes();
    }

    @GetMapping("/currency-distribution")
    public List<Map<String, Object>> getCurrencyDistribution() {
        return reportService.getCurrencyDistribution();
    }

    @GetMapping("/top-senders")
    public List<Map<String, Object>> getTopSenders() {
        return reportService.getTopSenders();
    }

    @GetMapping("/location-based-transactions")
    public List<Map<String, Object>> getLocationBasedTransactions() {
        return reportService.getLocationBasedTransactions();
    }
}
