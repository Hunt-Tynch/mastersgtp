package com.masters.hga.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masters.hga.dto.HuntDTO;
import com.masters.hga.services.HuntService;

@RestController
@CrossOrigin("*")
@RequestMapping("/hunt")
public class HuntController {

    @Autowired
    private HuntService huntService;

    @GetMapping
    public HuntDTO getHunt() {
        return huntService.getHunt();
    }

    @PostMapping
    public ResponseEntity<HuntDTO> postHunt(@RequestBody HuntDTO dto) {
        return ResponseEntity.ok(huntService.newHunt(dto));
    }

    @PutMapping()
    public ResponseEntity<HuntDTO> putHunt(@RequestBody HuntDTO dto) {
        if (huntService.getHunt().getId() != dto.getId()) {
            return new ResponseEntity<HuntDTO>(dto, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(huntService.updateHunt(dto));
    }

}
