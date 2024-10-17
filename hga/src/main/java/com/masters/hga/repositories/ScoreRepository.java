package com.masters.hga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.hga.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findByDogsNumber(Long number);

}
