package com.compass.demo_park_api.repository;

import java.util.Optional;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.compass.demo_park_api.entity.ClienteVaga;
import com.compass.demo_park_api.repository.projection.ClienteVagaProjection;

public interface ClienteVagaRepository extends JpaRepository<ClienteVaga, Long>{

    Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);

    long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

    Page<ClienteVagaProjection> findAllByClienteCpf(String cpf, Pageable pageable);
}
