package com.masters.hga.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Score (~Crossing) DAO. Keeps track of time and dogs involved.
 * Scores are kept with their associated Judges so that information will be
 * available there.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Score {
    /** Unique ID for scores */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Time of the score */
    private String time;
    /** List of Dogs involved */
    @ManyToMany
    @JoinTable(name = "score_dogs", joinColumns = @JoinColumn(name = "score_id"), inverseJoinColumns = @JoinColumn(name = "dogs_number"), uniqueConstraints = @UniqueConstraint(columnNames = {
            "score_id", "dogs_number" }))
    private List<Dog> dogs;

    /**
     * Initial constructor for a Score. Assigns a time and the list of dogs to add.
     * 
     * @param time Time of the score
     * @param dogs List of dogs involved
     */
    public Score(String time, List<Dog> dogs) {
        this(null, time, dogs);
    }
}
