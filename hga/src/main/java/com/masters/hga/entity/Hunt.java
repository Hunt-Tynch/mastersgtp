package com.masters.hga.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Hunt DAO. Holds name of the Hunt, dates, and the time interval for scoring.
 * Refernces to the Hunt are used in the Dog DAO to get the time interval.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hunts")
public class Hunt {

	/** Unique Id for each Hunt */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/** Name of the Hunt */
	private String name;
	/** Dates for the Hunt */
	private String date;
	/**
	 * Time interval for scoring (highest score is taken, and then time interval
	 * applies from there)
	 */
	private Integer timeInterval;

	/**
	 * Initial constructor for a Hunt.
	 *
	 * @param name     Name of Hunt
	 * @param date     Date of Hunt
	 * @param interval Time interval used for scoring
	 */
	public Hunt(final String name, final String date, final Integer interval) {
		this(null, name, date, interval);
	}
}
