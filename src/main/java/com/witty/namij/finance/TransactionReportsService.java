package com.witty.namij.finance;

import com.witty.namij.finance.models.Transaction;
import com.witty.namij.finance.models.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionReportsService {

  @Autowired private TransactionRepository transactionRepository;

  // 1. Total transactions by type
  public Map<String, Long> getTransactionCountByType() {
    List<Transaction> transactions = transactionRepository.findAll();
    return transactions.stream()
        .collect(Collectors.groupingBy(Transaction::getTransactionType, Collectors.counting()));
  }

  // 2. Daily transaction volume
  public Map<LocalDate, BigDecimal> getDailyTransactionVolume() {
    List<Transaction> transactions = transactionRepository.findAll();
    return transactions.stream()
        .collect(
            Collectors.groupingBy(
                t -> t.getTransactionDate().toLocalDate(),
                Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));
  }

  // 3. Top 5 senders by transaction amount
  public List<Map.Entry<String, BigDecimal>> getTopSenders() {
    List<Transaction> transactions = transactionRepository.findAll();
    return transactions.stream()
        .collect(
            Collectors.groupingBy(
                t -> t.getSender() != null ? t.getSender() : "Misc",
                Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)))
        .entrySet()
        .stream()
        .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
        .limit(5)
        .collect(Collectors.toList());
  }

  // 4. Transaction distribution by category
  public Map<String, Long> getTransactionDistributionByCategory() {
    List<Transaction> transactions = transactionRepository.findAll();
    return transactions.stream()
        .collect(
            Collectors.groupingBy(
                t -> t.getCategory() != null ? t.getCategory() : "Misc", Collectors.counting()));
  }

  // 5. Monthly average transaction amount
  public Map<String, BigDecimal> getMonthlyAverageTransactionAmount() {
    List<Transaction> transactions = transactionRepository.findAll();
    return transactions.stream()
        .collect(
            Collectors.groupingBy(
                t ->
                    t.getTransactionDate().toLocalDate().getYear()
                        + "-"
                        + t.getTransactionDate().toLocalDate().getMonth(),
                Collectors.averagingDouble(t -> t.getAmount().doubleValue())))
        .entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> BigDecimal.valueOf(e.getValue())));
  }
}
