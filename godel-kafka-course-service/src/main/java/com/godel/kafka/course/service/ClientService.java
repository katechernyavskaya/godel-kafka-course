package com.godel.kafka.course.service;

import com.godel.kafka.course.model.Client;
import com.godel.kafka.course.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository clientRepository;

  public void createClient(Client client) {
    clientRepository.save(client);
  }
}
