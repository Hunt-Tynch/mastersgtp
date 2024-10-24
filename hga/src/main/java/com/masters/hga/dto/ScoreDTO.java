package com.masters.hga.dto;

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
    private int time;

    private DogDTO firstDog;
    private DogDTO secondDog;
    private DogDTO thirdDog;
    private DogDTO fourthDog;
    private DogDTO fifthDog;
    private DogDTO sixthDog;
    private DogDTO seventhDog;
    private JudgeDTO judge;
    private int day;

    /**
     * Initial constructor for a Score. Assigns a time and the list of dogs to add.
     * 
     * @param time Time of the score
     * @param dogs List of dogs involved
     */
    public ScoreDTO(int time, JudgeDTO judge, int day, DogDTO... dogs) {
        this();
        setTime(time);
        setJudge(judge);
        setDay(day);
        switch (dogs.length) {
            case 7:
                setSeventhDog(dogs[6]);
            case 6:
                setSixthDog(dogs[5]);
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
