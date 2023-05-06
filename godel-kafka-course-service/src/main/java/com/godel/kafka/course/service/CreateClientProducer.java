package com.godel.kafka.course.service;

import com.godel.kafka.course.model.Client;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CreateClientProducer {

  private final KafkaTemplate<String, Client> createClientKafkaTemplate;
  private final String createClientTopic;

  public CreateClientProducer(KafkaTemplate<String, Client> createClientKafkaTemplate,
      @Value("${spring.kafka.producer.client-topic}")
      String createClientTopic) {
    this.createClientKafkaTemplate = createClientKafkaTemplate;
    this.createClientTopic = createClientTopic;
  }

  public boolean sendCreateClientEvent(Client client)
      throws ExecutionException, InterruptedException {
    SendResult<String, Client> sendResult = createClientKafkaTemplate.send(createClientTopic,
        client).get();
    log.info("Create client {} event sent via Kafka", client);
    log.info(sendResult.toString());
    return true;
  }

}
