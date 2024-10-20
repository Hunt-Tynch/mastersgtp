package com.masters.hga.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.masters.hga.dto.ScratchDTO;
import com.masters.hga.entity.Scratch;

@Mapper
public interface ScratchMapper {

    ScratchMapper INSTANCE = Mappers.getMapper(ScratchMapper.class);

    Scratch toEntity(ScratchDTO dto);

    ScratchDTO toDto(Scratch entity);

    List<Scratch> toEntityList(List<ScratchDTO> dtos);

    List<ScratchDTO> toDtoList(List<Scratch> entities);
}
