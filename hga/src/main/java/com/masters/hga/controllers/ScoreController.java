package com.masters.hga.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masters.hga.dto.ScoreDTO;
import com.masters.hga.services.ScoreService;

@RestController
@CrossOrigin("*")
@RequestMapping("/scores")
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping
    public List<ScoreDTO> getScores() {
        return scoreService.getAllScores();
    }

    @GetMapping("/judge/{id}")
    public List<ScoreDTO> getScoresForJudge(@PathVariable Long id) {
        if (scoreService.judgeExists(id)) {
            return scoreService.getAllScoresByJudge(id);
        }
        return null;
    }

    @GetMapping("/dog/{number}")
    public List<ScoreDTO> getScoresForDog(@PathVariable Long number) {
        if (scoreService.dogExists(number)) {
            return scoreService.getAllScoresByDog(number);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<ScoreDTO> postScore(@RequestBody ScoreDTO dto) {
        if (scoreService.validScore(dto)) {
            return ResponseEntity.ok(scoreService.createScore(dto));
        }
        return ResponseEntity.badRequest().body(dto);
    }

    @DeleteMapping("{id}")
    public void deleteScore(@PathVariable Long id) {
        if (scoreService.scoreExists(id)) {
            scoreService.deleteScore(id);
        }
    }

    @PutMapping
    public ResponseEntity<ScoreDTO> putScore(@RequestBody ScoreDTO dto) {
        if (scoreService.scoreExists(dto.getId())) {
            return ResponseEntity.ok(scoreService.updateScore(dto));
        }
        return ResponseEntity.badRequest().body(dto);
    }

}
