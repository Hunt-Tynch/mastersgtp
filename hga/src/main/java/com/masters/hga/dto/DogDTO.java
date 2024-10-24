package com.masters.hga.dto;

import com.masters.hga.entity.enums.StakeType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dog DTO. Each dog has a unique identifier (i.e number), and several other
 * assoicated values:
 * name, score, sire, dam, and kennel. Dogs share a many to one relationship
 * with the kennel,
 * and this will be used for retrieving dogs by kennel if necessary.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DogDTO {

    /** Hunt that the dog is in */
    private HuntDTO hunt;
    /** Number assigned to dog for hunt. (1-999) */
    private Long number;
    /** Stake (All-Age or Derby) based off number */
    private StakeType stake;
    /** Name of the dog. */
    private String name;
    /** Registration number for the Dog */
    private String regNumber;
    /** Speed and Drive Score for day 1. */
    private int sdscore1;
    /** Endurance Score for the day 1. */
    private int escore1;
    /** Speed and Drive Score for day 2. */
    private int sdscore2;
    /** Endurance Score for the day 2. */
    private int escore2;
    /** Speed and Drive Score for day 3. */
    private int sdscore3;
    /** Endurance Score for the day 3. */
    private int escore3;
    /** Speed and Drive Score for day 4. */
    private int sdscore4;
    /** Endurance Score for the day 4. */
    private int escore4;
    /** Total Score for the hunt */
    private int total;
    /** Sire of the dog. */
    private String sire;
    /** Dam of the dog */
    private String dam;
    /** Kennel the dog belongs to. */
    private KennelDTO kennel;

    private ScoreDTO currentScore;

    /**
     * Initial constructor for a Dog, assigns a score of 0 and assigns other values
     * as given.
     * 
     * @param number Number of the dog
     * @param name   Name of the dog
     * @param sire   Sire of the dog
     * @param dam    Dam of the dog
     * @param kennel Kennel of the dog
     */
    public DogDTO(HuntDTO hunt, Long number, StakeType stake, String name, String regNumber, String sire, String dam,
            KennelDTO kennel) {
        this(hunt, number, stake, name, regNumber, 0, 0, 0, 0, 0, 0, 0, 0, 0, sire, dam, kennel, null);
    }

}
