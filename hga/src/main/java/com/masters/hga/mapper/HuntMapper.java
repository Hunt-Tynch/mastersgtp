package com.masters.hga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.masters.hga.dto.HuntDTO;
import com.masters.hga.entity.Hunt;

@Mapper
public interface HuntMapper {
    HuntMapper INSTANCE = Mappers.getMapper(HuntMapper.class);

    Hunt toEntity(HuntDTO dto);

    HuntDTO toDto(Hunt entity);
}
