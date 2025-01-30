package com.compass.demo_park_api.service;

import org.springframework.stereotype.Service;

import com.compass.demo_park_api.entity.ClienteVaga;
import com.compass.demo_park_api.repository.ClienteVagaRepository;

import jakarta.persistence.EntityNotFoundException;
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

    @Transactional
    public ClienteVaga buscarPorRecibo(String recibo) {
        return repository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(
            () -> new EntityNotFoundException(
                    String.format("Recibo '%s' não encontrado no sistema, ou check-out já realizado", recibo)
            )
        );
    }

    @Transactional
    public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
        return repository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
    }
}
