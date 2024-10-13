package com.masters.hga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Kennel DAO. Stores the owner, their town, and their state. Used in Dog DAO
 * to distinguish dogs by Kennel.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Kennel {

    /** Unique Id for Kennel */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** Owners name for Kennel */
    private String owner;
    /** Owners town for Kennel */
    private String town;
    /** Owners state for Kennel */
    private String state;

    /**
     * Inital constructor for a Kennel. Sets the given fields owner, town, and
     * state.
     * 
     * @param owner Name of Kennel
     * @param town  Town of Kennel
     * @param state State of Kennel
     */
    public Kennel(String owner, String town, String state) {
        setId(null);
        setOwner(owner);
        setTown(town);
        setState(state);
    }

}
