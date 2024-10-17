package com.masters.hga.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.masters.hga.dto.KennelDTO;
import com.masters.hga.entity.Kennel;

@Mapper
public interface KennelMapper {

    KennelMapper INSTANCE = Mappers.getMapper(KennelMapper.class);

    Kennel toEntity(KennelDTO dto);

    KennelDTO toDto(Kennel entity);

}
