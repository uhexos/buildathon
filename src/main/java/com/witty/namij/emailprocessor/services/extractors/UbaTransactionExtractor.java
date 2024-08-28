package com.witty.namij.emailprocessor.services.extractors;

import com.witty.namij.emailprocessor.models.Message;
import com.witty.namij.emailprocessor.services.TransactionExtractor;
import com.witty.namij.finance.models.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class UbaTransactionExtractor extends TransactionExtractor {
  private static final DateTimeFormatter formatter =
      DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm", Locale.ENGLISH);

  @Override
  public Transaction extract(Message message) {
    String notification = extractTextFromUbaEmailTable(message.getHtml());
    //        String notification = message.getTextPlain();
    Transaction transactionDetails = new Transaction();
    transactionDetails.setAccountNumber(extractField(notification, "Account Number"));
    transactionDetails.setDescription(extractField(notification, "Transaction Narration"));
    transactionDetails.setDescription(extractField(notification, "Transaction Narration"));
    transactionDetails.setAmount(
        new BigDecimal(extractField(notification, "Transaction Amount").replaceAll(",", "")));
    transactionDetails.setDateTime(
        LocalDateTime.parse(extractField(notification, "Date and Time"), formatter));
    transactionDetails.setAvailableBalance(
        new BigDecimal(extractField(notification, "Available Balance").replaceAll(",", "")));
    transactionDetails.setCurrentBalance(
        new BigDecimal(extractField(notification, "Cleared Balance").replaceAll(",", "")));

    String transactionType = extractField(notification, "Transaction Type");
    if (transactionType.contains("CREDIT")) {
      transactionType = "CREDIT";
    }
    if (transactionType.contains("DEBIT")) {
      transactionType = "DEBIT";
    }
    transactionDetails.setTransactionType(transactionType);
    processDescriptionExtractInfoAndUpdateTransaction(transactionDetails);
    return transactionDetails;
  }

  @Override
  public boolean canHandle(Message message) {
    return true;
  }

  private void processDescriptionExtractInfoAndUpdateTransaction(Transaction transaction) {
    if (transaction.getDescription().contains("ATM CWD") || transaction.getDescription().contains("ATM WD")) {
      handleATMTransaction(transaction);
    }
  }
  private String extractTextFromUbaEmailTable(String html) {

    Document doc = Jsoup.parse(html);
    Elements rows = doc.select("table table tr");

    Map<String, String> extractedData = new HashMap<>();
    String[] keysToExtract = {
      "Account Number",
      "Transaction Narration",
      "Transaction Amount",
      "Transaction Type",
      "Date and Time",
      "Available Balance",
      "Cleared Balance"
    };

    for (Element row : rows) {
      Elements cells = row.select("td");
      if (cells.size() == 2) {
        String key = cells.get(0).text().trim();
        String value = cells.get(1).text().trim();

        for (String keyToExtract : keysToExtract) {
          if (key.equals(keyToExtract)) {
            extractedData.put(key, value);
            break;
          }
        }
      }
    }
    StringBuilder sb = new StringBuilder();
    // Print extracted data
    for (String key : keysToExtract) {
      sb.append(key).append(";; ").append(extractedData.getOrDefault(key, "N/A")).append("\n");
    }
    return sb.toString();
  }

  public void handleATMTransaction(Transaction transaction) {
    String[] parts = transaction.getDescription().split("-", 2);
    transaction.setLocation(parts.length > 1 ? parts[1].trim() : null);
    System.out.println("location"+  transaction.getLocation());
  }
}
