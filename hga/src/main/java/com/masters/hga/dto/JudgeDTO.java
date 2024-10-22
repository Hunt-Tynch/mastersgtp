package com.masters.hga.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Judge DTO. Stores Judges unique id, member PIN, and name. Also holds a
 * connection
 * to their scores and scratches.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JudgeDTO {

    /** Unique id for judge per hunt */
    private Long id;
    /** Judge's memberPIN */
    private String memberPIN;
    /** Judge's name */
    private String name;

}
