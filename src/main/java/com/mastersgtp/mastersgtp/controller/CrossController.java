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

import com.mastersgtp.mastersgtp.entity.Cross;
import com.mastersgtp.mastersgtp.service.CrossService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/cross")
public class CrossController {

    @Autowired
    private CrossService crossService;

    @GetMapping("/judge/{number}")
    public List<Cross> getCrossesByJudge(@PathVariable("number") int number) {
        return crossService.findByJudge(number);
    }

    @GetMapping("/dog/{number}")
    public List<Cross> getCrossesByDog(@PathVariable("number") int number) {
        return crossService.findByDog(number);
    }

    @GetMapping("/day/{day}")
    public List<Cross> getAllCrossesForDay(@PathVariable("day") int day) {
        return crossService.getAllCrossesForDay(day);
    }

    @PostMapping
    public String createCross(@RequestBody Cross cross) {
        return crossService.newCross(cross);
    }

    @DeleteMapping("/{id}")
    public void deleteCross(@PathVariable("id") Long id) {
        crossService.deleteCross(id);
    }

    @PutMapping("/{id}")
    public void editCross(@PathVariable("id") Long id, @RequestBody Cross cross) {
        crossService.editCross(id, cross);
    }
}
