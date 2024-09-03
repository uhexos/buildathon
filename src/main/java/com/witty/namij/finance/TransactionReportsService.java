package com.witty.namij.finance;

import com.witty.namij.finance.models.Transaction;
import com.witty.namij.finance.models.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionReportsService {
  @Autowired
  private TransactionRepository transactionRepository;

  private List<Transaction> getFilteredTransactions(LocalDate startDate, LocalDate endDate) {
    return transactionRepository.findAll().stream()
            .filter(t -> {
              LocalDate transactionDate = t.getTransactionDate().toLocalDate();
              return (startDate == null || !transactionDate.isBefore(startDate))
                      && (endDate == null || !transactionDate.isAfter(endDate));
            })
            .collect(Collectors.toList());
  }

  public Map<String, Long> getTransactionCountByType(LocalDate startDate, LocalDate endDate) {
    List<Transaction> transactions = getFilteredTransactions(startDate, endDate);
    return transactions.stream()
            .collect(Collectors.groupingBy(Transaction::getTransactionType, Collectors.counting()));
  }

  public Map<LocalDate, BigDecimal> getDailyTransactionVolume(LocalDate startDate, LocalDate endDate) {
    List<Transaction> transactions = getFilteredTransactions(startDate, endDate);
    return transactions.stream()
            .collect(
                    Collectors.groupingBy(
                            t -> t.getTransactionDate().toLocalDate(),
                            Collectors.reducing(BigDecimal.ZERO, Transaction::getAmount, BigDecimal::add)));
  }

  public List<Map.Entry<String, BigDecimal>> getTopSenders(LocalDate startDate, LocalDate endDate) {
    List<Transaction> transactions = getFilteredTransactions(startDate, endDate);
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

  public Map<String, Long> getTransactionDistributionByCategory(LocalDate startDate, LocalDate endDate) {
    List<Transaction> transactions = getFilteredTransactions(startDate, endDate);
    return transactions.stream()
            .collect(
                    Collectors.groupingBy(
                            t -> t.getCategory() != null ? t.getCategory() : "Misc",
                            Collectors.counting()));
  }

  public Map<String, BigDecimal> getMonthlyAverageTransactionAmount(LocalDate startDate, LocalDate endDate) {
    List<Transaction> transactions = getFilteredTransactions(startDate, endDate);
    return transactions.stream()
            .collect(
                    Collectors.groupingBy(
                            t -> t.getTransactionDate().toLocalDate().getYear() + "-" + t.getTransactionDate().toLocalDate().getMonth(),
                            Collectors.averagingDouble(t -> t.getAmount().doubleValue())))
            .entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e -> BigDecimal.valueOf(e.getValue())));
  }
}
