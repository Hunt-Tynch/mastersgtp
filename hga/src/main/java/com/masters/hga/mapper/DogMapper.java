package com.masters.hga.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.masters.hga.dto.DogDTO;
import com.masters.hga.entity.Dog;

@Mapper
public interface DogMapper {

    DogMapper INSTANCE = Mappers.getMapper(DogMapper.class);

    DogDTO toDTO(Dog entity);

    Dog toEntity(DogDTO dto);

    List<DogDTO> toDtoList(List<Dog> entities);

    List<Dog> toEntityList(List<DogDTO> dtos);
}
