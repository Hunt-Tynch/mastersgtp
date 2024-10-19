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

import com.masters.hga.dto.KennelDTO;
import com.masters.hga.services.KennelService;

@RestController
@CrossOrigin("*")
@RequestMapping("/kennels")
public class KennelController {

    @Autowired
    private KennelService kennelService;

    @GetMapping
    public List<KennelDTO> getKennels() {
        return kennelService.getAllKennels();
    }

    @PostMapping
    public ResponseEntity<KennelDTO> createKennel(@RequestBody KennelDTO dto) {
        return ResponseEntity.ok(kennelService.createKennel(dto));
    }

    @DeleteMapping("{id}")
    public void deleteKennel(@PathVariable Long id) {
        kennelService.deleteKennel(id);
    }

}
