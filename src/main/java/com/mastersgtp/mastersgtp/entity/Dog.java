package com.mastersgtp.mastersgtp.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dog {

    @Id
    private int number;

    private String name;

    private StakeType stake;

    private String regNumber;

    private String sire;

    private String dam;

    private String owner;

    private String ownerTown;

    private String ownerState;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn(name = "score_order")
    private List<Score> scores = new ArrayList<Score>();

    private int total;

    private boolean scratched = false;

    public Dog(int number, String name, StakeType stake, String regNumber, String sire, String dam, String owner,
            String ownerTown, String ownerState) {
        this(number, name, stake, regNumber, sire, dam, owner, ownerTown, ownerState, new ArrayList<Score>(), 0, false);
        scores.add(new Score(null, 0, 0, 0));
        scores.add(new Score(null, 0, 0, 1));
        scores.add(new Score(null, 0, 0, 2));
        scores.add(new Score(null, 0, 0, 3));
    }

    public void place(int index, int points) {
        scores.get(index).place(index, points, this);
    }

    public void deletePlace(int index, int points) {
        scores.get(index).deletePlace(index, points, this);
    }

    public int getSdscores() {
        int total = 0;
        for (Score s : scores) {
            total += s.getSdscore();
        }
        return total;
    }

    public int getEscores() {
        int total = 0;
        for (Score s : scores) {
            total += s.getEscore();
        }
        return total;
    }

    public int getSdscore(int index) {
        return scores.get(index).getSdscore();
    }

    public int getEscores(int index) {
        return scores.get(index).getEscore();
    }

}
