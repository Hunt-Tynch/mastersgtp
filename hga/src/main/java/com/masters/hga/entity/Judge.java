package com.masters.hga.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Judge DAO. Stores Judges unique id, member PIN, and name. Also holds a
 * connection
 * to their scores and scratches.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Judge {

    /** Unique id for judge per hunt */
    @Id
    private Integer id;
    /** Judge's memberPIN */
    private String memberPIN;
    /** Judge's name */
    private String name;
    /** List of scores made by Judge */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Score> scores;
    /** List of scratches made by Judge */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Scratch> scratches;

    public Judge(Integer id, String memberPIN, String name) {
        this(id, memberPIN, name, new ArrayList<Score>(), new ArrayList<Scratch>());
    }

}
