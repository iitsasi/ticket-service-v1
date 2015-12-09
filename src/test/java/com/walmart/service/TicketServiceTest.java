/**
 * 
 */
package com.walmart.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.walmart.domain.SeatHold;
import com.walmart.domain.constants.SeatStatus;



/**
 * @author Sasidhara Marrapu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application-context.xml"})
public class TicketServiceTest {
	
	@Autowired
	TicketService ticketService;

	

	/**
	 * Test method for {@link com.walmart.service.TicketServiceImpl#numSeatsAvailable(java.util.Optional)}.
	 */
	@Test
	public  void testNumSeatsAvailable() {
		
        Assert.assertNotEquals(ticketService.numSeatsAvailable(Optional.of(1)), 0);
        Assert.assertEquals(ticketService.numSeatsAvailable(Optional.of(0)), 0);
        
	}

	/**
	 * Test method for {@link com.walmart.service.TicketServiceImpl#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)}.
	 */
	@Test
	public  void testFindAndHoldSeats1() {
		/* Test 1: OnHoldTest
		 * 3 seats from level 1 
		 */
		SeatHold onHold = ticketService.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "iitsasi@gmail.com");
		for(int i=0; i<onHold.getSeatsToHold().size();i++){
			//status of the seat should be ONHOLD
			Assert.assertEquals(SeatStatus.ONHOLD, onHold.getSeatsToHold().get(0).getStatus());
		}
	}
	
	/**
	 * Test method for {@link com.walmart.service.TicketServiceImpl#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)}.
	 */
	@Test
	public  void testFindAndHoldSeats2() {
		/* Test 2: data test
		 * 3 seats from level 1 
		 */
		//3 seats from level 1 
		SeatHold threeSeatsFirstLevel = ticketService.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "iitsasi@gmail.com");
		//SeatHold should have only request seat counts
		Assert.assertEquals(3,threeSeatsFirstLevel.getSeatsToHold().size());
		
		//SeatHold should have the email id updated
		Assert.assertEquals("iitsasi@gmail.com",threeSeatsFirstLevel.getCustomerEmail());
		
		//3 seats from level 2 
		SeatHold threeSeatsSecondLevel = ticketService.findAndHoldSeats(3, Optional.of(2), Optional.of(3), "iitsasi@gmail.com");
		
		//bothObjects should not be equal
		Assert.assertNotEquals(threeSeatsFirstLevel, threeSeatsSecondLevel);
		
		//both should have different seats
		Assert.assertNotEquals(threeSeatsFirstLevel.getSeatsToHold().get(0), threeSeatsSecondLevel.getSeatsToHold().get(0));
		
		//Both should have different id
		Assert.assertNotEquals(threeSeatsFirstLevel.getId(), threeSeatsSecondLevel.getId());
	}

	/**
	 * Test method for {@link com.walmart.service.TicketServiceImpl#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)}.
	 */
	@Test
	public  void testFindAndHoldSeats3() {
		/* Test 3: Duplicate hold
		 * 3 seats from level 1 
		 */
		//3 seats from level 1 
		SeatHold threeSeatsFirstLevel = ticketService.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "iitsasi@gmail.com");
				
		//3 seats from level 2 
		SeatHold threeSeatsSecondLevel = ticketService.findAndHoldSeats(3, Optional.of(2), Optional.of(3), "iitsasi@gmail.com");
		
		//bothObjects should not be equal
		Assert.assertNotEquals(threeSeatsFirstLevel, threeSeatsSecondLevel);
		
		//Both should have different id
		Assert.assertNotEquals(threeSeatsFirstLevel.getId(), threeSeatsSecondLevel.getId());
	}

	/**
	 * Test method for {@link com.walmart.service.TicketServiceImpl#findAndHoldSeats(int, java.util.Optional, java.util.Optional, java.lang.String)}.
	 */
	@Test
	public  void testFindAndHoldSeats4() {
		/* Test 4: hold expire
		 * 3 seats from level 1 
		 */
		SeatHold holdAndExpiry = ticketService.findAndHoldSeats(3, Optional.of(2), Optional.of(3), "imgengaraj@gmail.com");
		try {
			Thread.sleep((30*1000)+1);
			//after 30 secs(20000ms), SeatHold should expire
			Assert.assertFalse(holdAndExpiry.getSeatsToHold().get(0).isOnHold());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Test method for {@link com.walmart.service.TicketServiceImpl#reserveSeats(int, java.lang.String)}.
	 */
	@Test
	public  void testReserveSeats() {
		//Holding 3 seats from level 1 
		SeatHold seatHold = ticketService.findAndHoldSeats(3, Optional.of(1), Optional.of(3), "iitsasi@gmail.com");
		
		String confirmId = ticketService.reserveSeats(seatHold.getId(),"iitsasi@gmail.com");
		
		//Should return the confirmation id, should not be null
		Assert.assertNotNull(confirmId);
		
		//After reserving,status should be changed to "RESERVED"
		for(int i=0; i<seatHold.getSeatsToHold().size();i++){
			Assert.assertEquals(SeatStatus.RESERVED, seatHold.getSeatsToHold().get(i).getStatus());
		}
		
		//Duplication reservation should return the message "Sorry, Seats are not available/expired. Please try again"
		String duplicateReservation = ticketService.reserveSeats(seatHold.getId(),"iitsasi@gmail.com");
		Assert.assertEquals("Sorry, Seats are no longer available/expired. Please try again",duplicateReservation);
				
	}

	/**
	 * @return the ticketService
	 */
	public TicketService getTicketService() {
		return ticketService;
	}

	/**
	 * @param ticketService the ticketService to set
	 */
	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}
}
