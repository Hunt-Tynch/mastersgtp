package com.masters.hga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Kennel DTO. Stores the owner, their town, and their state. Used in Dog DAO
 * to distinguish dogs by Kennel.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KennelDTO {

    /** Unique Id for Kennel */
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
    public KennelDTO(String owner, String town, String state) {
        setId(null);
        setOwner(owner);
        setTown(town);
        setState(state);
    }

}
