package com.compass.demo_park_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.compass.demo_park_api.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
}
