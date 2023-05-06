package com.godel.kafka.course.controller;

import com.godel.kafka.course.model.Client;
import com.godel.kafka.course.service.CreateClientProducer;
import java.util.concurrent.ExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/clients")
@RestController
public class ClientController {

  private final CreateClientProducer createClientProducer;


  public ClientController(CreateClientProducer createClientProducer) {
    this.createClientProducer = createClientProducer;
  }

  @PostMapping
  public ResponseEntity<?> createClient(@RequestBody Client client)
      throws ExecutionException, InterruptedException {
    createClientProducer.sendCreateClientEvent(client);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
