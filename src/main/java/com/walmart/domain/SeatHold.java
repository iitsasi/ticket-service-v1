/**
 * 
 */
package com.walmart.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Sasidhara Marrapu
 *
 */
public class SeatHold implements Serializable {

	private static final long serialVersionUID = 1326010690994209400L;

	private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

	private int id;

	private int numberOfSeats;

	private long expireTime;

	private List<Seat> seatsToHold;

	private String customerEmail;

	private String errorMessage;

	public SeatHold(int numberOfSeats, String customerEmail) {
		this.id = ID_GENERATOR.incrementAndGet();
		this.numberOfSeats = numberOfSeats;
		this.seatsToHold = new ArrayList<Seat>(numberOfSeats);
		this.customerEmail = customerEmail;

	}

	/**
	 * Check for the expiration of the hold
	 * 
	 * @return boolean
	 */
	public boolean isOnHold() {
		return System.currentTimeMillis() <= this.expireTime;
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
	 * @return the numberOfSeats
	 */
	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	/**
	 * @param numberOfSeats
	 *            the numberOfSeats to set
	 */
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	/**
	 * @return the expireTime
	 */
	public long getExpireTime() {
		return expireTime;
	}

	/**
	 * @param holdTimexpireTimeeMillis
	 *            the expireTime to set
	 */
	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * @return the seatsToHold
	 */
	public List<Seat> getSeatsToHold() {
		return seatsToHold;
	}

	/**
	 * @param seatToHold
	 *            the seatsToHold to set
	 */
	public void setSeatsToHold(List<Seat> seatsToHold) {
		this.seatsToHold = seatsToHold;
	}

	/**
	 * @param seatToHold
	 *            the seatToHold to set
	 */
	public void addSeatToHold(Seat seatToHold) {
		this.seatsToHold.add(seatToHold);
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * @param customerEmail
	 *            the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
