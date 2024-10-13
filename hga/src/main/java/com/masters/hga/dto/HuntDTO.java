package com.masters.hga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Hunt DTO. Stores name, date, and time interval for Hunt.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HuntDTO {

    /** Unique Id for each Hunt */
    private Integer id;
    /** Name of the Hunt */
    private String name;
    /** Dates for the Hunt */
    private String date;
    /**
     * Time interval for scoring (highest score is taken, and then time interval
     * applies from there)
     */
    private Integer interval;

    /**
     * Initial constructor for a Hunt.
     * 
     * @param name     Name of Hunt
     * @param date     Date of Hunt
     * @param interval Time interval used for scoring
     */
    public HuntDTO(String name, String date, Integer interval) {
        this(null, name, date, interval);
    }
}
