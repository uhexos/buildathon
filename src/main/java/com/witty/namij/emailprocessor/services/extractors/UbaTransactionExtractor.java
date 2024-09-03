package com.witty.namij.emailprocessor.services.extractors;

import com.witty.namij.emailprocessor.models.Message;
import com.witty.namij.emailprocessor.services.TransactionExtractor;
import com.witty.namij.finance.models.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    transactionDetails.setTransactionDate(
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
    if (transaction.getDescription().contains("ATM CWD")
        || transaction.getDescription().contains("ATM WD")) {
      handleATMTransaction(transaction);
    } else if (transaction.getDescription().contains("STAMP")
        && transaction.getDescription().contains("DUTY")) {
      handleStampDutyTransaction(transaction);
    } else if (transaction.getDescription().contains("Interest")
        && transaction.getDescription().contains("Paid")) {
      handleInterestTransaction(transaction);
    } else if (transaction.getDescription().contains("MOB")
        || transaction.getDescription().contains("MOB2")) {
      handMobileTransaction(transaction);
    } else if (transaction.getDescription().contains("NXG")) {
      handNXGTransaction(transaction);
    } else if (transaction.getDescription().toLowerCase().contains("pos")
        && (transaction.getDescription().toLowerCase().contains("pur")
            || transaction.getDescription().toLowerCase().contains("trf"))) {
      handPOSTransaction(transaction);
    } else if (transaction.getDescription().toLowerCase().contains("tnf")) {
      handleTNFTransaction(transaction);
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

  private void handleATMTransaction(Transaction transaction) {
    String[] parts = transaction.getDescription().split("-", 2);
    transaction.setLocation(parts.length > 1 ? parts[1].trim() : null);
    transaction.setCategory("ATM");
  }

  private void handleStampDutyTransaction(Transaction transaction) {
    //    ATM CWD @ 11012153-114 ELETU OGADI, ADEOLAODEKU V
    //    ATM WD @ 10331256-UBA H MACAULAY 2 ATM2 YABA
    transaction.setCategory("STAMP DUTY");
  }

  private void handleInterestTransaction(Transaction transaction) {
    //    Interest Paid 01-05-2024 to 31-05-2024
    transaction.setCategory("INTEREST");
  }

  private void handMobileTransaction(Transaction transaction) {
    //    MOB/UTO/500chow Fra/food /19877589899
    //    MOB2/UTO/To NGOZI NWOKOROBIA/pos
    //    MOB2/UTU/From UGOCHUKWU NWOKOROBIA To BASHIR SHAGA

    String[] parts = transaction.getDescription().split("/");

    for (int i = 0; i < parts.length; i++) {
      String part = parts[i].trim();
      String lowerPart = part.toLowerCase();

      if (lowerPart.startsWith("from")) {

        Pattern pattern = Pattern.compile("from (.*?) to (.*)");
        Matcher matcher = pattern.matcher(lowerPart);

        if (matcher.find()) {
          String fromName = matcher.group(1);
          String toName = matcher.group(2);

          transaction.setSender(fromName);
          transaction.setReceiver(toName);
        }
      } else if (lowerPart.startsWith("to")) {
        transaction.setReceiver(part.replaceFirst("(?i)To\\s*", "").trim());
      } else if (i == 2 && transaction.getReceiver() == null) {
        // If third part and no receiver set, assume it's the receiver
        transaction.setReceiver(part);
      }
      if (transaction.getCategory() == null && i == 3) {
        // If we haven't set a category and it's  the third part, assume it's the category
        transaction.setCategory(part);
      }
    }
  }

  private void handNXGTransaction(Transaction transaction) {
    //    NXG :TRFDebtpaymentforwifiFRMIFUNEYACHUK
    //    NXG :TRFFuelexpensefortheofficeFRMIFUNEY
    //    NXG :TRFPaymentfordetergentFRMIFUNEYACHU

    // Define the regex pattern to match the required sections
    Pattern pattern = Pattern.compile("TRF(.*?)FRM(\\w+)");
    Matcher matcher = pattern.matcher(transaction.getDescription());

    if (matcher.find()) {
      String expenseSection = matcher.group(1);
      String senderSection = matcher.group(2);

      transaction.setSender(senderSection);
      transaction.setCategory(expenseSection);
    }
  }

  private void handPOSTransaction(Transaction transaction) {

    // Define the regex pattern to match the required sections
    //    TODO implememnt this too many vairables to be effective with regex
  }

  private void handleTNFTransaction(Transaction transaction) {

    // Define regex patterns
    List<String> matchedNames = new ArrayList<>();

    String[] patterns = {
      "TNF-([^/]+)/.*", // Pattern 1: TNF-Name/Additional info
      "TNF-([A-Z ]+)/.*", // Pattern 2: TNF-FULL NAME IN CAPS/Additional info
      "TNF-([A-Z][a-z]+ [A-Z][a-z]+ [A-Z][a-z]+)/.*", // Pattern 3: TNF-First Middle Last/Additional
                                                      // info
      "TNF-([A-Z ]+)(?:[A-Z ]+)/.*", // Pattern 4: TNF-NAME REPEATED/Additional info
      "TNF-([A-Z-]+ [A-Z.]+ [A-Z]+)[A-Z., -]+", // Pattern 5: TNF-NAME WITH HYPHEN AND INITIAL
      "TNF-([A-Z]+ [A-Z]+ [A-Z]+)[A-Z ]+", // Pattern 6: TNF-NAME REPE
    };

    for (String patternStr : patterns) {
      Pattern pattern = Pattern.compile(patternStr);
      Matcher matcher = pattern.matcher(transaction.getDescription());

      while (matcher.find()) {
        matchedNames.add(matcher.group(1).trim());
      }
    }
    if (!matchedNames.isEmpty()) {
      transaction.setReceiver(matchedNames.get(0));
    }
    //    System.out.println(matchedNames+ transaction.getDescription());
  }
}
