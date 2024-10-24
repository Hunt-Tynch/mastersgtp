package com.masters.hga.entity;

import org.hibernate.annotations.Formula;

import com.masters.hga.entity.enums.StakeType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    /** Speed and Drive Score for day 1. */
    private int sdscore1;
    /** Endurance Score for the day 1. */
    @Formula("CASE WHEN sdscore1 >= 60 THEN sdscore1 * 0.1 ELSE 0 END")
    private int escore1;
    /** Speed and Drive Score for day 2. */
    private int sdscore2;
    /** Endurance Score for the day 2. */
    @Formula("CASE WHEN sdscore2 >= 60 THEN sdscore2 * 0.2 ELSE 0 END")
    private int escore2;
    /** Speed and Drive Score for day 3. */
    private int sdscore3;
    /** Endurance Score for the day 3. */
    @Formula("CASE WHEN sdscore3 >= 60 THEN sdscore3 * 0.3 ELSE 0 END")
    private int escore3;
    /** Speed and Drive Score for day 4. */
    private int sdscore4;
    /** Endurance Score for the day 4. */
    @Formula("CASE WHEN sdscore4 >= 60 THEN sdscore4 * 0.4 ELSE 0 END")
    private int escore4;
    /** Total Score for the hunt */
    @Formula("sdscore1 + " +
            "(CASE WHEN sdscore1 >= 60 THEN sdscore1 * 0.1 ELSE 0 END) + " +
            "sdscore2 + " + //
            "(CASE WHEN sdscore2 >= 60 THEN sdscore2 * 0.2 ELSE 0 END) + " +
            "sdscore3 + " +
            "(CASE WHEN sdscore3 >= 60 THEN sdscore3 * 0.3 ELSE 0 END) + " +
            "sdscore4 + " +
            "(CASE WHEN sdscore4 >= 60 THEN sdscore4 * 0.4 ELSE 0 END)")
    private int total;
    /** Sire of the dog. */
    private String sire;
    /** Dam of the dog */
    private String dam;
    /** Kennel the dog belongs to. */
    @ManyToOne(fetch = FetchType.LAZY)
    private Kennel kennel;

    @OneToOne
    private Score currentScore;

    public Dog(Hunt hunt, Long number, StakeType stake, String name, String regNumber, String sire, String dam,
            Kennel kennel) {
        this(hunt, number, stake, name, regNumber, 0, 0, 0, 0, 0, 0, 0, 0, 0, sire, dam, kennel, null);
    }

    public void place(int points, int day) {
        switch (day) {
            case 1:
                sdscore1 += points;
                break;
            case 2:
                sdscore2 += points;
                break;
            case 3:
                sdscore3 += points;
            case 4:
                sdscore4 += points;
        }
    }

    public void removePlace(int points, int day) {
        switch (day) {
            case 1:
                sdscore1 -= points;
                break;
            case 2:
                sdscore2 -= points;
                break;
            case 3:
                sdscore3 -= points;
            case 4:
                sdscore4 -= points;
        }
    }

}
