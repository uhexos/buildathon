package com.witty.namij.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TransactionReportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getTransactionVolumeOverTime() {
        String sql = "SELECT DATE(transaction_date) AS date, COUNT(*) AS transaction_count FROM transaction GROUP BY DATE(transaction_date)";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getTotalAmountTransactedOverTime() {
        String sql = "SELECT DATE(transaction_date) AS date, SUM(amount) AS total_amount FROM transaction GROUP BY DATE(transaction_date)";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getTopTransactionTypes() {
        String sql = "SELECT transaction_type, COUNT(*) AS transaction_count FROM transaction GROUP BY transaction_type";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getCurrencyDistribution() {
        String sql = "SELECT currency, COUNT(*) AS transaction_count FROM transaction GROUP BY currency";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getTopSenders() {
        String sql = "SELECT sender, COUNT(*) AS transaction_count FROM transaction GROUP BY sender ORDER BY transaction_count DESC LIMIT 10";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Map<String, Object>> getLocationBasedTransactions() {
        String sql = "SELECT location, COUNT(*) AS transaction_count FROM transaction GROUP BY location";
        return jdbcTemplate.queryForList(sql);
    }
}
