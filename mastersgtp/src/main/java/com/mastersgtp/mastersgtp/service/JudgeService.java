package com.mastersgtp.mastersgtp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mastersgtp.mastersgtp.entity.Judge;
import com.mastersgtp.mastersgtp.repository.JudgeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JudgeService {

    private JudgeRepository judgeRepository;

    public Judge newJudge(Judge judge) {
        if (judgeRepository.existsById(judge.getNumber())) {
            return null;
        }
        return judgeRepository.save(judge);
    }

    public void deleteJudge(int id) {
        judgeRepository.deleteById(id);
    }

    public void editJudge(int id, Judge judge) {
        if (!judgeRepository.existsById(id))
            return;
        Judge savedJudge = judgeRepository.findById(id).get();
        savedJudge.setName(judge.getName());
        savedJudge.setPin(judge.getPin());
        judgeRepository.save(savedJudge);
    }

    public List<Judge> getJudges() {
        return judgeRepository.findAll();
    }

}
