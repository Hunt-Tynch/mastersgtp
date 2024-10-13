package com.masters.hga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.masters.hga.dto.ScoreDTO;
import com.masters.hga.entity.Score;

@Mapper
public interface ScoreMapper {

    ScoreMapper INSTANCE = Mappers.getMapper(ScoreMapper.class);

    Score toEntity(ScoreDTO dto);

    ScoreDTO toDto(Score entity);

}
