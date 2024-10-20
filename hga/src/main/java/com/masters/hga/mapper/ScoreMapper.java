package com.masters.hga.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.masters.hga.dto.ScoreDTO;
import com.masters.hga.entity.Score;

@Mapper
public interface ScoreMapper {

    ScoreMapper INSTANCE = Mappers.getMapper(ScoreMapper.class);

    Score toEntity(ScoreDTO dto);

    ScoreDTO toDto(Score entity);

    List<Score> toEntityList(List<ScoreDTO> dtos);

    List<ScoreDTO> toDtoList(List<Score> entities);

}
