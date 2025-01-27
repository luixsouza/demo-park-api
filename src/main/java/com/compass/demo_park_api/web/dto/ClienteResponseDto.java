package com.compass.demo_park_api.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ClienteResponseDto {

    private Long id;
    private String nome;
    private String cpf;
}
