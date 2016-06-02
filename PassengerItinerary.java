import java.util.ArrayList;
import java.util.Scanner;

public class PassengerItinerary {
	public static Scanner input = new Scanner(System.in);
	
	public static void main(String[] args) {
		//TODO infinite loop, change later when functions are used
		do{
			PassengerLoginSelection();
		}while(true);
	}
	
	public static void PassengerLoginSelection() {
		boolean Menu2 = true;
		String firstName = "";
		String lastName = "";
		do{
			System.out.println("======= Menu 1 =======");
			System.out.println("1. Create a new itinerary.");
			System.out.println("2. Manage existing itinerary.");
			System.out.println("3. Exit the program.");
			System.out.print("Type the number corresponding to the action you wish to complete: ");
			int choice = getValidatedInt(1, 3);
			
			if(choice == 1) {
				//new passenger
				String[] name = getValidatedName();
				firstName = name[0];
				lastName = name[1];
				System.out.println("newPassenger: " + firstName + " " + lastName);
				PassengerItineraryManagement(firstName, lastName);
			} else if(choice == 2) {
				//modify itinerary
				String[] name = getValidatedName();
				firstName = name[0];
				lastName = name[1];
				System.out.println("findPassenger: " + lastName);
				PassengerItineraryManagement(firstName, lastName);
			} else {
				//exit program to menu 2
				System.exit(0);
			}
		}while(Menu2);
		return;
	}
	
	public static void PassengerItineraryManagement(String firstName, String lastName) {
		boolean menu = false;
		System.out.println();
		System.out.println("======= Menu 2 =======");
		System.out.println("1. Search for a flight / flight sequence.");
		System.out.println("2. Cancel one flight on the itinerary.");
		System.out.println("3. Cancel all fights on the itinerary.");
		System.out.println("4. Return to menu 1.");
		System.out.print("Type the number corresponding to the action you wish to complete: ");
		int choice = getValidatedInt(1, 4);
		
		String[] flight = new String[2];
		String origin = null;
		String destination = null;
		String[] allFlights = new String[0];
		
		if(choice == 1) {
			//search for flight
			flight = getValidatedFlight();
			origin = flight[0];
			destination = flight[1];
			System.out.println("findAvailableFlightPlans: " + origin + " " + destination);
		} else if(choice == 2) {
			//cancel one flight
			allFlights = RandomItinerary.get();
			for(int i = 0 ; i < allFlights.length; i++) {
				System.out.println((i + 1) + ". " + allFlights[i]);
			}
			System.out.print("Type the number of the flight you wish to cancel: ");
			int cancelThis = getValidatedInt(1,(allFlights.length+1));
			System.out.println("cancelFlight: " + allFlights[cancelThis - 1] + " " +  firstName + " " + lastName );			
		} else if(choice == 3) {
			//cancel all flights
			allFlights = RandomItinerary.get();
			for(int i = 0 ; i < allFlights.length; i++) {
				System.out.println("cancelFlight: " + allFlights[i] + " " +  firstName + " " + lastName );
			}
			
		} else {
			//return to menu 1
			return;
		}
		
		System.out.println();
	}
		
	public static int getValidatedInt(int min, int max)
	{
		int tempNum = 0;
			if(input.hasNextInt())
			{
				tempNum = input.nextInt();
				if(tempNum < min || tempNum > max)
				{
					System.out.println("Error: value is larger than 3 or less than 1. Try again.");
				}
				else
				{
					return tempNum;
				}
			}
			else
			{
				System.out.println("Error - non-integer input detected!");
				input.next();
			}
		return tempNum;
	}
	
	public static String[] getValidatedName() {
		System.out.print("Type the passengers first name: ");
		String[] name = new String[2];
		name[0] = input.next();
		System.out.print("Type the passengers last name: ");
		name[1] = input.next();
 		return name;
	}
	
	public static String[] getValidatedFlight() {
		String[] flightCode = new String[2];
		String[] codes = {"GNV", "BTR", "MCO", "MIA", "ATL", "IAH", "LAX", "JFK", "LGA", "ORD", "BOS", "ANC", "DEN", "SLC", "SFO", "IAD", "SEA", "OKC"};
		
		//TODO make it search array and make sure code match the list...
		
		boolean gotCodes = true;
		do{
			System.out.println("Type the 3 letter origin code follow by the 3 letter destination code (eg. GNV MCO ): ");
			
			flightCode[0] = input.next();
			flightCode[1] = input.next();
			
			boolean origin = false;
			boolean destination = false;
			
			for(int i = 0; i < codes.length; i++) {
					 if (flightCode[1].equalsIgnoreCase(codes[i])) {
					     destination = true;
					 }
					if (flightCode[0].equalsIgnoreCase(codes[i])) {
					    origin = true;   
					 }
			}
			
			if(origin == true & destination == true) {
				gotCodes = false;
			}
				
			if(flightCode[0].length() != 3 || flightCode[1].length() != 3 || destination == false || origin == false) {
				System.out.println("One of the airport codes was entered incorrectly! Try again! ");
				gotCodes = true;
			}
			
		}while(gotCodes);
		
		return flightCode;
	}

}