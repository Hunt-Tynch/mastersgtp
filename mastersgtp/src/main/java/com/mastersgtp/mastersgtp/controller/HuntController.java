package com.mastersgtp.mastersgtp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastersgtp.mastersgtp.entity.Hunt;
import com.mastersgtp.mastersgtp.service.HuntService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/hunt")
public class HuntController {

    @Autowired
    private HuntService huntService;

    @PostMapping
    public Hunt createHunt(@RequestBody Hunt hunt) {
        return huntService.newHunt(hunt);
    }

    @PutMapping("/start-time/{day}/{startTime}")
    public void setStartTime(@PathVariable int day, @PathVariable int startTime) {
        huntService.setStartTime(day, startTime);
    }

    @PutMapping
    public Hunt editHunt(@RequestBody Hunt hunt) {
        return huntService.editHunt(hunt);
    }

    @GetMapping
    public Hunt getHunt() {
        return huntService.getHunt();
    }
}
