package com.mastersgtp.mastersgtp.entity;

import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mastersgtp.mastersgtp.DogPointsDeserializer;
import com.mastersgtp.mastersgtp.DogPointsSerializer;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "cross_dogs", joinColumns = @JoinColumn(name = "cross_id"))
    @MapKeyJoinColumn(name = "dog_number")
    @Column(name = "points")
    @JsonSerialize(using = DogPointsSerializer.class)
    @JsonDeserialize(using = DogPointsDeserializer.class)
    private Map<Dog, Integer> dogs;

    private int day;

    private int crossTime;

    public Cross(Judge judge, Map<Dog, Integer> dogs, int day, int time) {
        this(null, judge, dogs, day, time);
    }

}
