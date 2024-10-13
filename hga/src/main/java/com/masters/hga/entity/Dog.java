package com.masters.hga.entity;

import jakarta.persistence.CascadeType;
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

    /** Number assigned to dog for hunt. (1-999) */
    @Id
    private Integer number;
    /** Name of the dog. */
    private String name;
    /** Registration number for the Dog */
    private String regNumber;
    /** Score for the hunt. */
    private Integer score;
    /** Sire of the dog. */
    private String sire;
    /** Dam of the dog */
    private String dam;
    /** Kennel the dog belongs to. */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
    public Dog(Integer number, String name, String regNumber, String sire, String dam, Kennel kennel) {
        this(number, name, regNumber, 0, sire, dam, kennel);
    }

}
