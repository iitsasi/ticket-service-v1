/**
 * 
 */
package com.walmart.dao;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.walmart.domain.Level;
import com.walmart.domain.constants.MockLevelData;

/**
 * This is dao implementaion that persist and retrieve the data from the
 * database we are mocking the data here, this should be replaced with the ORM
 * operations.
 * 
 * @author Sasidhara Marrapu
 *
 */
public class TicketDao {

	private static Map<Integer, Level> venue = new ConcurrentHashMap<Integer, Level>();

	/*
	 * This method is to initialize the seats and the levels and there
	 * parameters. We need not use this if we are getting connected to database.
	 */
	static {
		for (MockLevelData data : MockLevelData.values()) {
			venue.put(data.getId(), getLevel(data));
		}

	}

	private static Level getLevel(MockLevelData data) {
		return new Level(data.getId(), data.getName(), data.getPrice(), data.getNoOFRows(), data.getNoOfCols());
	}

	/**
	 * This method gets the all the available seats in level, if level is passed
	 * otherwise return for all levels.
	 * 
	 * @param venueLevel
	 * @return
	 */
	public int getAvailableSeats(Optional<Integer> venueLevel) {
		int availableSeats = 0;

		if (null != venueLevel && venueLevel.isPresent()) {
			if (venueLevel.get() > 0) {
				return venue.get(venueLevel.get()).getNumberOfSeatsAvailable();
			}
		} else {
			// Return default all the levels
			for (Integer level : venue.keySet()) {
				availableSeats = availableSeats + venue.get(level).getNumberOfSeatsAvailable();
			}
		}
		return availableSeats;
	}

	public Level getVenueLevel(int venueLevel) throws Exception {

		if (venueLevel < 1 && venueLevel > 4) {
			throw new Exception("Sorry, invalid venue level");
		}

		return venue.get(venueLevel);
	}

}
