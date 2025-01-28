package com.compass.demo_park_api.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.compass.demo_park_api.entity.Vaga;
import com.compass.demo_park_api.web.dto.VagaCreateDto;
import com.compass.demo_park_api.web.dto.VagaResponseDto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDto dto) {
        return new ModelMapper().map(dto, Vaga.class);
    }

    public static VagaResponseDto tDto(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDto.class);
    }
}
