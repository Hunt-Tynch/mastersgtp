package com.mastersgtp.mastersgtp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mastersgtp.mastersgtp.entity.Scratch;
import com.mastersgtp.mastersgtp.service.ScratchService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/scratch")
public class ScratchController {

    @Autowired
    private ScratchService scratchService;

    @PostMapping
    public String createScratch(@RequestBody Scratch scratch) {
        return scratchService.newScratch(scratch);
    }

    @DeleteMapping("/{id}")
    public void deleteScratch(@PathVariable Long id) {
        scratchService.deleteScratch(id);
    }

    @GetMapping()
    public List<Scratch> getScratches() {
        return scratchService.getScratch();
    }

}
