package com.masters.hga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private Long id;
    /** Judge's memberPIN */
    private String memberPIN;
    /** Judge's name */
    private String name;

}
