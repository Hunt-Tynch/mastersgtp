package com.masters.hga.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Score (~Crossing) DAO. Keeps track of time and dogs involved.
 * Scores are kept with their associated Judges so that information will be
 * available there.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "idx_first_dog", columnList = "first_dog_id"),
        @Index(name = "idx_second_dog", columnList = "second_dog_id"),
        @Index(name = "idx_third_dog", columnList = "third_dog_id"),
        @Index(name = "idx_fourth_dog", columnList = "fourth_dog_id"),
        @Index(name = "idx_fifth_dog", columnList = "fifth_dog_id")
})
public class Score {
    /** Unique ID for scores */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /** Time of the score */
    private String time;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Dog firstDog;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Dog secondDog;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Dog thirdDog;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Dog fourthDog;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Dog fifthDog;

    /**
     * Initial constructor for a Score. Assigns a time and the list of dogs to add.
     * 
     * @param time Time of the score
     * @param dogs List of dogs involved
     */
    public Score(String time, Dog... dogs) {
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
