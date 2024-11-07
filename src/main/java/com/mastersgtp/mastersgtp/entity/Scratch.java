package com.mastersgtp.mastersgtp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Scratch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "judge_number")
    private Judge judge;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Dog dog;

    private String reason;

    private int time;

    public Scratch(Judge judge, Dog dog, String reason, int time) {
        this(null, judge, dog, reason, time);
    }

}
