package com.masters.hga.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masters.hga.entity.Hunt;

public interface HuntRepository extends JpaRepository<Hunt, Long> {

    List<Hunt> findByName(String name);

}
