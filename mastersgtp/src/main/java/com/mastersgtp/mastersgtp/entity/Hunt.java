package com.mastersgtp.mastersgtp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "hunts")
public class Hunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String date;

    private int timeInterval;

    private int[] startTimes;

    public Hunt(String name, String date, int timeInterval, int[] startTimes) {
        this(null, name, date, timeInterval, startTimes);
    }

    public void setStartTime(int day, int startTime) {
        startTimes[day] = startTime;
    }

}
