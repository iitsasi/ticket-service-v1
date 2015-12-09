/**
 * 
 */
package com.walmart;

/**
 * @author Sasidhara Marrapu
 *
 */
import java.util.Optional;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.walmart.domain.SeatHold;
import com.walmart.service.TicketService;

@Component
public class App {
	
	
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			ApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
			TicketService ticketService = (TicketService) context.getBean("ticketService");
		
			boolean repeat = true;
			Scanner scanner = new Scanner(System.in);
			while (repeat) {
				
				System.out.println("Please enter the option you want to try..");
				System.out.println("Enter 1 for SeatAvailabilty");
				System.out.println("Enter 2 for Hold & Reserve");
				
				int option = scanner.nextInt();
				if (option == 1) {
					
					System.out.println("Please enter any values from 1 to 4 to find the available seats \n");
					System.out.println("Available Seats: "
							+ ticketService.numSeatsAvailable(Optional.of(Integer.valueOf(scanner.nextInt()))));
					
				} else if (option == 2) {
					
					System.out.println(" To hold seat enter the below values \n");
					System.out.println("Please enter number of Seats to be held\n");
					int numberOfSeats = scanner.nextInt();
					
					System.out.println("Please enter MINIMUM venue level (from 1 to 4  or -1 to skip) \n");
					int minLevel = scanner.nextInt();
					
					System.out.println("Please enter MAXIMUM venue level (from 1 to 4 or -1 to skip) \n");
					int maxLevel = scanner.nextInt();
					System.out.println("Please enter email address \n");
					
					String email = scanner.next();
					SeatHold seatHold = ticketService.findAndHoldSeats(numberOfSeats, Optional.of(minLevel),
							Optional.of(maxLevel), email);
					System.out.println("Requested Seats are ONHOLD, id# " + seatHold.getId());

					for (int i = 0; i < seatHold.getSeatsToHold().toArray().length; i++) {
						System.out.print(seatHold.getSeatsToHold().toArray()[i] + " ");
					}

					System.out.print("\nPlease enter 1 to reserve the seats held \n");
					if (scanner.nextInt() == 1) {
						System.out.println(ticketService.reserveSeats(seatHold.getId(), email));
					} else {
						System.out.println("Seats are not confirmed");
					}

				}
				System.out.println("Please press 1 if you want to continue again or press any other number to terminate");
				if (scanner.nextInt() != 1) {
					repeat = false;
				}

			}
			scanner.close();

		} catch (Exception exception) {
			System.out.println("Please enter the valid input : ");
			exception.printStackTrace();
		}

		System.out.println("Completed");

	}

	
}
