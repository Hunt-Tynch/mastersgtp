package com.masters.hga.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Judge DTO. Stores Judges unique id, member PIN, and name. Also holds a
 * connection
 * to their scores and scratches.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JudgeDTO {

    /** Unique id for judge per hunt */
    private Long id;
    /** Judge's memberPIN */
    private String memberPIN;
    /** Judge's name */
    private String name;
    /** List of scores made by Judge */
    private List<ScoreDTO> scores;
    /** List of scratches made by Judge */
    private List<ScratchDTO> scratches;

    public JudgeDTO(Long id, String memberPIN, String name) {
        this(id, memberPIN, name, new ArrayList<ScoreDTO>(), new ArrayList<ScratchDTO>());
    }

}
