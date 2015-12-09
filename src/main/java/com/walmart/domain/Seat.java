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
public class Seat implements Serializable {

	private static final long serialVersionUID = 1162753972457739410L;

	private Level venueLevel;

	private int seatNumber;

	private SeatStatus status = SeatStatus.AVAILABLE;

	private long holdExpireTime;

	private long holdReserveId;

	public Seat(int seatNumber, Level venueLevel) {
		this.seatNumber = seatNumber;
		this.venueLevel = venueLevel;
	}

	/**
	 * @return the venueLevel
	 */
	public Level getVenueLevel() {
		return venueLevel;
	}

	/**
	 * @param venueLevel
	 *            the venueLevel to set
	 */
	public void setVenueLevel(Level venueLevel) {
		this.venueLevel = venueLevel;
	}

	/**
	 * @return the seatNumber
	 */
	public int getSeatNumber() {
		return seatNumber;
	}

	/**
	 * @param seatNumber
	 *            the seatNumber to set
	 */
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	/**
	 * @return the status
	 */
	public SeatStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(SeatStatus status) {
		this.status = status;
	}

	/**
	 * @return the holdExpireTime
	 */
	public long getHoldExpireTime() {
		return holdExpireTime;
	}

	/**
	 * @param holdStartTime
	 *            the holdExpireTime to set
	 */
	public void setHoldExpireTime(long holdExpireTime) {
		this.holdExpireTime = holdExpireTime;
	}

	/**
	 * @return the reservationId
	 */
	public long getHoldReserveId() {
		return holdReserveId;
	}

	/**
	 * @param reservationId
	 *            the reservationId to set
	 */
	public void setholdReserveId(long holdReserveId) {
		this.holdReserveId = holdReserveId;
	}

	/**
	 * This method returns true if the object is on hold
	 * 
	 * @return
	 */
	public boolean isOnHold() {
		if (SeatStatus.ONHOLD.equals(status)) {
			return System.currentTimeMillis() <= this.holdExpireTime;
		}
		return true;
	}

	public boolean isAvailable() {
		if (SeatStatus.AVAILABLE.equals(status)) {
			return true;
		} else if (SeatStatus.ONHOLD.equals(status) && System.currentTimeMillis() >= this.holdExpireTime) {
			this.status = SeatStatus.AVAILABLE;
			this.getVenueLevel().updateAvailability(SeatStatus.AVAILABLE);
			return true;
		}
		return false;
	}

	@Override
	// To print the all seats of this Avenue Level with level id , seat number
	// and status with just viewable foramt
	public String toString() {
		return new StringBuilder("\n Seat ==>------------------------------").append("\n Seat Number=").append(this.seatNumber)
				.append("\n Venue Level=").append(this.venueLevel.getId())
				.append("\n Status=").append(this.status)
				.toString();

	}

}
