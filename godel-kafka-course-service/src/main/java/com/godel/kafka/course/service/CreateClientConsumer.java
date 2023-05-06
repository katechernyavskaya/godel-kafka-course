package com.godel.kafka.course.service;

import com.godel.kafka.course.model.Client;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CreateClientConsumer {

  private ClientService clientService;

  @KafkaListener(topics = "${spring.kafka.producer.client-topic}", containerFactory = "ClientContainerFactory")
  public void createClientListener(@Payload Client client, Acknowledgment ack) {
    log.info("Consumed client creation", client.getEmail());
    clientService.createClient(client);
    ack.acknowledge();
  }
}
