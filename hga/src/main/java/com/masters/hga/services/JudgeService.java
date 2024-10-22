package com.masters.hga.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masters.hga.dto.JudgeDTO;
import com.masters.hga.dto.ScoreDTO;
import com.masters.hga.entity.Judge;
import com.masters.hga.mapper.JudgeMapper;
import com.masters.hga.repositories.JudgeRepository;

@Service
public class JudgeService {

    @Autowired
    private JudgeRepository judgeRepository;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ScratchService scratchService;

    public boolean exists(Long id) {
        return judgeRepository.existsById(id);
    }

    public JudgeDTO createJudge(JudgeDTO dto) {
        Judge judge = JudgeMapper.INSTANCE.toEntity(dto);
        return JudgeMapper.INSTANCE.toDto(judgeRepository.save(judge));
    }

    public void deleteJudge(Long id) {
        if (exists(id)) {
            List<ScoreDTO> scores = scoreService.getAllScoresByJudge(id);
            for (ScoreDTO s : scores) {
                scoreService.deleteScore(s.getId());
            }
            judgeRepository.deleteById(id);
        }
    }

    public List<JudgeDTO> getJudges() {
        return JudgeMapper.INSTANCE.toDtoList(judgeRepository.findAll());
    }

    public JudgeDTO getJudge(Long id) {
        return JudgeMapper.INSTANCE.toDto(judgeRepository.findById(id).get());
    }

    public JudgeDTO editJudge(JudgeDTO dto) {
        if (exists(dto.getId())) {
            Judge j = judgeRepository.findById(dto.getId()).get();
            j.setMemberPIN(dto.getMemberPIN());
            j.setName(dto.getName());
            return JudgeMapper.INSTANCE.toDto(judgeRepository.save(j));
        }
        return null;
    }

}
