/**
 * 
 */
package com.walmart.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walmart.dao.TicketDao;
import com.walmart.domain.Level;
import com.walmart.domain.Seat;
import com.walmart.domain.SeatHold;
import com.walmart.domain.constants.SeatStatus;

/**
 * @author Sasidhara Marrapu
 *
 */
@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketDao ticketDao;

	private long holdTimeSecs = 30;

	private Map<Integer, SeatHold> seatsHold = new ConcurrentHashMap<Integer, SeatHold>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.walmart.service.TicketService#numSeatsAvailable(java.util.Optional)
	 */
	public int numSeatsAvailable(Optional<Integer> venueLevel) {
		return ticketDao.getAvailableSeats(venueLevel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.walmart.service.TicketService#findAndHoldSeats(int,
	 * java.util.Optional, java.util.Optional, java.lang.String)
	 */
	// Assumption is minLevel = 1 least desirable, maxLevel =4 best available
	public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel,
			String customerEmail) {

		SeatHold seatHold = new SeatHold(numSeats, customerEmail);

		if (numSeats <= 0) {
			seatHold.setErrorMessage("Sorry, Invalid Number of Seats Requested, Please try again");
			return seatHold;
		}

		if (numSeats > numSeatsAvailable(null)) {
			seatHold.setErrorMessage("Sorry, only" + numSeatsAvailable(null) + "seats are available");
			return seatHold;
		}

		int startLevel = 1;
		int endLevel = 4;

		if (null != minLevel && minLevel.isPresent() &&  minLevel.get() > 0)
			startLevel = minLevel.get();

		if (null != maxLevel && maxLevel.isPresent() && maxLevel.get() > 0)
			endLevel = maxLevel.get();

		// Get Best Available Seats
		levelLoop: for (int i = startLevel; i <= endLevel; i++) {
			Level level;
			try {
				level = ticketDao.getVenueLevel(i);

				synchronized (level) {

					for (Seat[] seatRow : level.getSeats()) {
						for (Seat seat : seatRow) {
							if (seat.isAvailable()) {
								seat.setStatus(SeatStatus.ONHOLD);
								seat.getVenueLevel().updateAvailability(SeatStatus.ONHOLD);
								seat.setHoldExpireTime(System.currentTimeMillis() + holdTimeSecs * 1000);
								seat.setholdReserveId(seatHold.getId());
								seatHold.addSeatToHold(seat);
							}
							if (seatHold.getSeatsToHold().size() == numSeats)
								break levelLoop;
						}
						if (seatHold.getSeatsToHold().size() == numSeats)
							break levelLoop;
					}
				}
			} catch (Exception e) {
				seatHold.setErrorMessage(e.getMessage());
				return seatHold;
			}

		}
		seatHold.setCustomerEmail(customerEmail);
		seatHold.setExpireTime(System.currentTimeMillis() + holdTimeSecs * 1000);
		getSeatsHold().put(seatHold.getId(), seatHold);

		return seatHold;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.walmart.service.TicketService#reserveSeats(int,
	 * java.lang.String)
	 */
	public String reserveSeats(int seatHoldId, String customerEmail) {

		if(!getSeatsHold().containsKey(seatHoldId)){
			return "Sorry, Seats are no longer available/expired. Please try again";
		}
		SeatHold seatHold = getSeatsHold().get(seatHoldId);
		List<Seat> seatsToReserve = seatHold.getSeatsToHold();

		synchronized (seatsHold) {
			if (seatHold.isOnHold()) {
				for (Seat seat : seatsToReserve) {
					seat.setStatus(SeatStatus.RESERVED);
					seat.getVenueLevel().updateAvailability(SeatStatus.RESERVED);

				}
				seatHold.setExpireTime(System.currentTimeMillis());
				seatsHold.remove(seatHold.getId());
			} else {
				for (Seat seat : seatsToReserve) {
					/*
					 * This check will make sure that this functionality is
					 * resetting the status if seats are belongs to this
					 * seatHoldId booking
					 */
					if (seat.getHoldReserveId() == seatHoldId) {
						seat.setStatus(SeatStatus.AVAILABLE);
						seat.getVenueLevel().updateAvailability(SeatStatus.AVAILABLE);
					}
				}
				return "Sorry, Seats are no longer available/expired. Please try again";
			}
		}
		return String.valueOf(seatHold.getId());
	}

	/**
	 * @return the seatsHold
	 */
	public Map<Integer, SeatHold> getSeatsHold() {
		return seatsHold;
	}

	/**
	 * @param seatsHold
	 *            the seatsHold to set
	 */
	public void setSeatsHold(Map<Integer, SeatHold> seatsHold) {
		this.seatsHold = seatsHold;
	}

	/**
	 * @return the ticketDao
	 */
	public TicketDao getTicketDao() {
		return ticketDao;
	}

	/**
	 * @param ticketDao the ticketDao to set
	 */
	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}

	
	/**
	 * @return the holdTimeSecs
	 */
	public long getHoldTimeSecs() {
		return holdTimeSecs;
	}

	/**
	 * @param holdTimeSecs the holdTimeSecs to set
	 */
	public void setHoldTimeSecs(long holdTimeSecs) {
		this.holdTimeSecs = holdTimeSecs;
	}
}
