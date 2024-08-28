//package com.witty.namij;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.dsl.Pollers;
//import org.springframework.integration.mail.dsl.Mail;
//
//@Configuration
//public class MailIntegrationConfig {
//
//  @Bean
//  public IntegrationFlow mainIntegration(EmailProperties props) {
//    return IntegrationFlow.from(
//            Mail.imapInboundAdapter(props.getImapUrl())
//                .shouldDeleteMessages(false)
//                .simpleContent(true)
//                .autoCloseFolder(false)
//                .javaMailProperties(p -> p.put("mail.debug", "true")),
//            e -> e.poller(Pollers.fixedDelay(props.getPollRate())))
//        .handle(
//            message -> {
//              System.out.println("New message received: " + message);
//            })
//        .get();
//  }
//}
