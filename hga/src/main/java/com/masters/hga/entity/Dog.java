package com.masters.hga.entity;

import com.masters.hga.entity.enums.StakeType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Dog DAO. Each dog has a unique identifier (i.e number), and several other
 * assoicated values:
 * name, score, sire, dam, and kennel. Dogs share a many to one relationship
 * with the kennel,
 * and this will be used for retrieving dogs by kennel if necessary.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dog {

    /** Hunt that the dog is in */
    @ManyToOne(fetch = FetchType.LAZY)
    private Hunt hunt;
    /** Number assigned to dog for hunt. (1-999) */
    @Id
    private Long number;
    /** Stake (All-Age or Derby) based off number */
    private StakeType stake;
    /** Name of the dog. */
    private String name;
    /** Registration number for the Dog */
    private String regNumber;
    /** Speed and Drive Score for the hunt. */
    private Integer sdscore;
    /** Endurance Score for the hunt. */
    private Integer escore;
    /** Total Score for the hunt */
    private Integer total;
    /** Sire of the dog. */
    private String sire;
    /** Dam of the dog */
    private String dam;
    /** Kennel the dog belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    private Kennel kennel;

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
    public Dog(Hunt hunt, Long number, StakeType stake, String name, String regNumber, String sire, String dam,
            Kennel kennel) {
        this(hunt, number, stake, name, regNumber, 0, 0, 0, sire, dam, kennel);
    }

}
