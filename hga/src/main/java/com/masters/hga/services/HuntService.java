package com.masters.hga.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masters.hga.dto.HuntDTO;
import com.masters.hga.entity.Hunt;
import com.masters.hga.mapper.HuntMapper;
import com.masters.hga.repositories.DogRepository;
import com.masters.hga.repositories.HuntRepository;
import com.masters.hga.repositories.JudgeRepository;
import com.masters.hga.repositories.KennelRepository;
import com.masters.hga.repositories.ScoreRepository;
import com.masters.hga.repositories.ScratchRepository;

@Service
public class HuntService {

    @Autowired
    private HuntRepository huntRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private KennelRepository kennelRepository;

    @Autowired
    private JudgeRepository judgeRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ScratchRepository scratchRepository;

    public HuntDTO newHunt(HuntDTO dto) {
        huntRepository.deleteAll();
        dogRepository.deleteAll();
        kennelRepository.deleteAll();
        judgeRepository.deleteAll();
        scoreRepository.deleteAll();
        scratchRepository.deleteAll();
        Hunt hunt = HuntMapper.INSTANCE.toEntity(dto);
        Hunt savedHunt = huntRepository.save(hunt);
        return HuntMapper.INSTANCE.toDto(savedHunt);
    }

    public HuntDTO updateHunt(HuntDTO dto) {
        Hunt hunt = huntRepository.findById(dto.getId()).get();
        hunt.setName(dto.getName());
        hunt.setInterval(dto.getInterval());
        hunt.setDate(dto.getDate());
        Hunt savedHunt = huntRepository.save(hunt);
        return HuntMapper.INSTANCE.toDto(savedHunt);
    }

}
