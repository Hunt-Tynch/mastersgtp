package com.masters.hga.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.masters.hga.dto.JudgeDTO;
import com.masters.hga.entity.Judge;

@Mapper
public interface JudgeMapper {

    JudgeMapper INSTANCE = Mappers.getMapper(JudgeMapper.class);

    Judge toEntity(JudgeDTO dto);

    JudgeDTO toDto(Judge entity);

    List<JudgeDTO> toDtoList(List<Judge> entities);

    List<Judge> toEntityList(List<JudgeDTO> dtos);
}
