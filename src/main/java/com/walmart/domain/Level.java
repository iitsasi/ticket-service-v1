/**
 * 
 */
package com.walmart.domain;

import java.io.Serializable;

import com.walmart.domain.constants.SeatStatus;

/**
 * @author Sasidhara Marrapu
 *
 */
public class Level implements Serializable {

	private static final long serialVersionUID = -257967709165637657L;

	private int id;
	private String name;
	private double price;
	private int numberOfRows;
	private int numberOfColumns;
	private Seat seats[][];
	private int numberOfSeatsAvailable;
	private int numberOfSeatsHeld;

	/*
	 * initializing the venue levels with full Available Seats by default for
	 * the given details
	 */
	public Level(int id, String name, double price, int rows, int seatsPerRow) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.numberOfRows = rows;
		this.numberOfColumns = seatsPerRow;
		this.numberOfSeatsAvailable = numberOfRows * numberOfColumns;
		seats = new Seat[numberOfRows][numberOfColumns];

		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				seats[i][j] = new Seat(i * numberOfColumns + j + 1, this);
			}
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the numberOfRows
	 */
	public int getNumberOfRows() {
		return numberOfRows;
	}

	/**
	 * @param numberOfRows
	 *            the numberOfRows to set
	 */
	public void setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
	}

	/**
	 * @return the numberOfColumns
	 */
	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	/**
	 * @param numberOfColumns
	 *            the numberOfColumns to set
	 */
	public void setNumberOfColumns(int numberOfColumns) {
		this.numberOfColumns = numberOfColumns;
	}

	/**
	 * @return the seats
	 */
	public Seat[][] getSeats() {
		return seats;
	}

	/**
	 * @param seats
	 *            the seats to set
	 */
	public void setSeats(Seat[][] seats) {
		this.seats = seats;
	}

	/**
	 * @return the numberOfSeatsAvailable
	 */
	public int getNumberOfSeatsAvailable() {
		return numberOfSeatsAvailable;
	}

	/**
	 * @param numberOfSeatsAvailable
	 *            the numberOfSeatsAvailable to set
	 */
	public void setNumberOfSeatsAvailable(int numberOfSeatsAvailable) {
		this.numberOfSeatsAvailable = numberOfSeatsAvailable;
	}

	/**
	 * @return the numberOfSeatsHeld
	 */
	public int getNumberOfSeatsHeld() {
		return numberOfSeatsHeld;
	}

	/**
	 * @param numberOfSeatsHeld
	 *            the numberOfSeatsHeld to set
	 */
	public void setNumberOfSeatsHeld(int numberOfSeatsHeld) {
		this.numberOfSeatsHeld = numberOfSeatsHeld;
	}

	// Updating the seat count for each status
	public void updateAvailability(SeatStatus statusChange) {
		switch (statusChange) {
		case ONHOLD:
			numberOfSeatsHeld++;
			numberOfSeatsAvailable--;
			break;
		case RESERVED:
			numberOfSeatsHeld--;
			break;
		case AVAILABLE:
			numberOfSeatsHeld--;
			numberOfSeatsAvailable++;
		default:
			break;
		}
	}
}
