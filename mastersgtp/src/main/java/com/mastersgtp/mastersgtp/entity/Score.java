package com.mastersgtp.mastersgtp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int sdscore;

    private int escore;

    private int day;

    public void place(int points, Dog d) {

        sdscore += points;
        double change = 0;
        if (sdscore >= 60) {
            change = ((.1 * (day + 1)) * sdscore) - escore;
            escore += change;
        }
        d.setTotal(sdscore + escore);
    }

    public void deletePlace(int points, Dog d) {
        sdscore -= points;
        double change = 0;
        if (sdscore < 60) {
            if (escore > 0) {
                escore = 0;
            }
        } else {
            change = ((.1 * (day + 1)) * sdscore) - escore;
            escore += change;
        }
        d.setTotal(sdscore + escore);
    }

}
