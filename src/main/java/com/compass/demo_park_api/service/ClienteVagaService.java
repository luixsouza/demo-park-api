package com.compass.demo_park_api.service;

import org.springframework.stereotype.Service;

import com.compass.demo_park_api.entity.ClienteVaga;
import com.compass.demo_park_api.repository.ClienteVagaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ClienteVagaService {

    private final ClienteVagaRepository repository;

    @Transactional
    public ClienteVaga salvar(ClienteVaga clienteVaga) {
        return repository.save(clienteVaga);
    }
}
