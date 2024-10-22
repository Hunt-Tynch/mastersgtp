package com.masters.hga.dto;

import com.masters.hga.entity.enums.ScratchType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Scratch DTO. Holds connected dog dao, reason for scratch, and the time.
 * Scratches are stored within Judge DAO's so the Judge is known.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScratchDTO {

    /** Unique id */
    private Long id;
    /** Dog that is scratched */
    private DogDTO dog;
    /** Reason for being scratched */
    private ScratchType reason;
    /** Time of scratch */
    private String time;
    private JudgeDTO judge;

    /**
     * Initial constructor for a scratch.
     * 
     * @param dog    Dog to be scratched
     * @param reason Reason for scratch
     * @param time   Time of scratch
     */
    public ScratchDTO(DogDTO dog, ScratchType reason, String time, JudgeDTO judge) {
        this(null, dog, reason, time, judge);
    }

}
