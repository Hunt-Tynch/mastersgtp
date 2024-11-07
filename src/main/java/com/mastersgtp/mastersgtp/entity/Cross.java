package com.mastersgtp.mastersgtp.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "crosses")
public class Cross {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "judge_number")
    private Judge judge;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @OrderColumn(name = "dog_order")
    private List<Dog> dogs;

    private int day;

    private int crossTime;

    public Cross(Judge judge, List<Dog> dogs, int day, int time) {
        this(null, judge, dogs, day, time);
    }

}
