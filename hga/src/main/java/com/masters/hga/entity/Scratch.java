package com.masters.hga.entity;

import com.masters.hga.entity.enums.ScratchType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Scratch DAO. Holds connected dog dao, reason for scratch, and the time.
 * Scratches are stored within Judge DAO's so the Judge is known.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Scratch {

    /** Unique id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** Dog that is scratched */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Dog dog;
    /** Reason for being scratched */
    private ScratchType reason;
    /** Time of scratch */
    private String time;

    /**
     * Initial constructor for a scratch.
     * 
     * @param dog    Dog to be scratched
     * @param reason Reason for scratch
     * @param time   Time of scratch
     */
    public Scratch(Dog dog, ScratchType reason, String time) {
        this(null, dog, reason, time);
    }

}
