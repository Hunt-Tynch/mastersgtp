package com.masters.hga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masters.hga.dto.JudgeDTO;
import com.masters.hga.services.JudgeService;

@RestController
@CrossOrigin("*")
@RequestMapping("/judges")
public class JudgeController {

    @Autowired
    private JudgeService judgeService;

    @GetMapping
    public List<JudgeDTO> getJudges() {
        return judgeService.getJudges();
    }

    @GetMapping("{id}")
    public JudgeDTO getJudge(@PathVariable Long id) {
        if (judgeService.exists(id)) {
            return judgeService.getJudge(id);
        } else {
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<JudgeDTO> postJudge(@RequestBody JudgeDTO dto) {
        if (judgeService.exists(dto.getId())) {
            return ResponseEntity.badRequest().body(dto);
        }
        return ResponseEntity.ok(judgeService.createJudge(dto));
    }

    public JudgeDTO putJudge(@RequestBody JudgeDTO dto) {
        return judgeService.editJudge(dto);
    }

    @DeleteMapping("{id}")
    public void deleteJudge(@PathVariable Long id) {
        judgeService.deleteJudge(id);
    }

}
