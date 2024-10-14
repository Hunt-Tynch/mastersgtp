package com.masters.hga.dto;

import java.util.List;

import com.masters.hga.entity.Dog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Score (~Crossing) DTO. Keeps track of time and dogs involved.
 * Scores are kept with their associated Judges so that information will be
 * available there.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDTO {
    /** Unique ID for scores */
    private Long id;
    /** Time of the score */
    private String time;
    /** List of Dogs involved */
    private List<Dog> dogs;

    /**
     * Initial constructor for a Score. Assigns a time and the list of dogs to add.
     * 
     * @param time Time of the score
     * @param dogs List of dogs involved
     */
    public ScoreDTO(String time, List<Dog> dogs) {
        this(null, time, dogs);
    }
}
