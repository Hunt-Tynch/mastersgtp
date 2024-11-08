package com.mastersgtp.mastersgtp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastersgtp.mastersgtp.entity.Judge;
import com.mastersgtp.mastersgtp.service.JudgeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/judge")
public class JudgeController {

    @Autowired
    private JudgeService judgeService;

    @PostMapping
    public Judge createJudge(@RequestBody Judge judge) {
        return judgeService.newJudge(judge);
    }

    @DeleteMapping("/{id}")
    public void deleteJudge(@PathVariable int id) {
        judgeService.deleteJudge(id);
    }

    @PutMapping("/{id}")
    public void editJudge(@PathVariable int id, @RequestBody Judge judge) {
        judgeService.editJudge(id, judge);
    }

    @GetMapping
    public List<Judge> getAllJudges() {
        return judgeService.getJudges();
    }
}
