//package com.witty.namij.emailprocessor;
//
//import com.witty.namij.emailprocessor.annotations.BankExtractor;
//import com.witty.namij.emailprocessor.services.TransactionExtractor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//import java.util.Map;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//@Configuration
//public class TransactionExtractorConfig {
//
//    @Bean
//    public Map<String, TransactionExtractor> transactionExtractors(List<TransactionExtractor> extractors) {
//        return extractors.stream()
//                .collect(Collectors.toMap(
//                        extractor -> extractor.getClass().getAnnotation(BankExtractor.class).bankName(),
//                        Function.identity()
//                ));
//    }
//}