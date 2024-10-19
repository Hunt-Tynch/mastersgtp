package com.masters.hga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masters.hga.dto.KennelDTO;
import com.masters.hga.entity.Kennel;
import com.masters.hga.mapper.KennelMapper;
import com.masters.hga.repositories.DogRepository;
import com.masters.hga.repositories.KennelRepository;

@Service
public class KennelService {

    @Autowired
    private KennelRepository kennelRepository;

    @Autowired
    private DogRepository dogRepository;

    public KennelDTO createKennel(KennelDTO dto) {
        Kennel kennel = KennelMapper.INSTANCE.toEntity(dto);
        return KennelMapper.INSTANCE.toDto(kennelRepository.save(kennel));
    }

    public void deleteKennel(Long id) {
        dogRepository.deleteAllInBatch(dogRepository.findByKennelOrderByTotalDesc(kennelRepository.findById(id).get()));
        kennelRepository.deleteById(id);
    }

    public List<KennelDTO> getAllKennels() {
        return KennelMapper.INSTANCE.toDtoList(kennelRepository.findAll());
    }
}