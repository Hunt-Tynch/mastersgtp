package com.masters.hga.dto;

import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Score (~Crossing) DTO. Keeps track of time and dogs involved.
 * Scores are kept with their associated Judges so that information will be
 * available there.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDTO {
    /** Unique ID for scores */
    private Long id;
    /** Time of the score */
    private String time;
    @OneToOne
    private DogDTO firstDog;
    @OneToOne
    private DogDTO secondDog;
    @OneToOne
    private DogDTO thirdDog;
    @OneToOne
    private DogDTO fourthDog;
    @OneToOne
    private DogDTO fifthDog;

    /**
     * Initial constructor for a Score. Assigns a time and the list of dogs to add.
     * 
     * @param time Time of the score
     * @param dogs List of dogs involved
     */
    public ScoreDTO(String time, DogDTO... dogs) {
        this();
        setTime(time);
        switch (dogs.length) {
            case 5:
                setFifthDog(dogs[4]);
            case 4:
                setFourthDog(dogs[3]);
            case 3:
                setThirdDog(dogs[2]);
            case 2:
                setSecondDog(dogs[1]);
            default:
                setFirstDog(dogs[0]);
                break;
        }
    }
}
