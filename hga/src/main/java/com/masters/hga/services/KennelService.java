package com.masters.hga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masters.hga.dto.KennelDTO;
import com.masters.hga.entity.Kennel;
import com.masters.hga.mapper.KennelMapper;
import com.masters.hga.repositories.KennelRepository;

@Service
public class KennelService {

    @Autowired
    private KennelRepository kennelRepository;

    public KennelDTO createKennel(KennelDTO dto) {
        Kennel kennel = KennelMapper.INSTANCE.toEntity(dto);
        return KennelMapper.INSTANCE.toDto(kennelRepository.save(kennel));
    }

    public void deleteKennel(KennelDTO dto) {
        kennelRepository.delete(KennelMapper.INSTANCE.toEntity(dto));
    }

}
