import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;


public class GeneralImportTest 
{	
	private static ArrayList<Flight> originalFlightList;
	private static ArrayList<Passenger> originalPassengerList;
	
	private static String filenameForImport = "CustomDataTest.txt";
	
	/**
	 * Set up the original version of the Flight list and Passenger lists in this method, 
	 * saving them in the two fields listed above.  The rest of this JUnit test case will
	 * then match the file being imported to your data.
	 */
	@BeforeClass
	public static void initializeOriginalData()
	{
		originalFlightList = new ArrayList<Flight>();
		
		Flight f1 = new Flight("MCO", "MIA", 800, 930, 30);
		Flight f2 = new Flight("ATL", "MIA", 1000, 1245, 120);
		Flight f3 = new Flight("MIA", "LAX", 1430, 1615, 150);
		Flight f4 = new Flight("BTR", "ATL", 730, 930, 20);
		Flight f5 = new Flight("ATL", "GNV", 1030, 1145, 20);
		Flight f6 = new Flight("ATL", "JFK", 1230, 1545, 120);
		Flight f7 = new Flight("LAX", "MIA", 530, 930, 135);
		Flight f8 = new Flight("MIA", "ATL", 930, 1200, 125);
		
		originalFlightList.add(f1);
		originalFlightList.add(f2);
		originalFlightList.add(f3);
		originalFlightList.add(f4);
		originalFlightList.add(f5);
		originalFlightList.add(f6);
		originalFlightList.add(f7);
		originalFlightList.add(f8);
		
		originalPassengerList = new ArrayList<Passenger>();
		
		Passenger p1 = new Passenger("Joshua", "Horton");
		Passenger p2 = new Passenger("Adam", "Smith");
		Passenger p3 = new Passenger("Barack", "Obama");
		Passenger p4 = new Passenger("Tom", "Petty");
		Passenger p5 = new Passenger("Steven", "Spielberg");
		
		originalPassengerList.add(p1);
		originalPassengerList.add(p2);
		originalPassengerList.add(p3);
		originalPassengerList.add(p4);
		originalPassengerList.add(p5);
		
		p1.bookFlight(f4);
		p1.bookFlight(f5);
		
		p3.bookFlight(f7);
		p3.bookFlight(f8);
		p3.bookFlight(f6);
		
		p4.bookFlight(f7);
		p4.addAlert("Your flight from LAX to MIA is running late.");
		
		p5.bookFlight(f1);
		p5.bookFlight(f3);	}
	
	@Test
	public void testCompleteDataLoading() 
	{
		testInitialFlightLoading(); // If it fails that test, this one is an auto-fail.
		testInitialPassengerLoading(); // If it fails that test, this one is an auto-fail.
		testPassengerFlightCount(); // Ensures that we've loaded the right number of flights, not whether they're loaded correctly.

		// If we're here, we know that the basic information for Passengers and Flights is properly loaded.
		// But, are the flights loaded - and /properly/?  They should be linked.
		ImportData data = DataManager.importData(filenameForImport);

		for(Passenger p:data.passengers)
		{
			for(Flight f1:p.getBookedFlights())
			{
				boolean matched = false;
				
				for(Flight f2:data.flights)
				{
					if(doesFlightMatch(f1, f2))
					{
						if(f1 != f2)
						{
							fail("Reference mismatch - a duplicate Flight definition has been detected.  The imported Flight reference doesn't match the reference booked by this Passenger, although the data matches.\n  Data: " + flightToString(f2) + " <=/=> " + passToString(p));
						}
						
						matched = true;
						break;
					}
				}
				
				if(!matched) fail("Passenger is booked on a Flight not found in ANY way within the imported list of Flights!");
			}
		}
	}
	
	@Test
	public void testPassengerFlightCount() 
	{
		/*
		 * WARNING - this test only checks whether the number of flights loaded is correct, NOT whether they CORRECTLY match.
		 * You can pass this method and still bomb the actual flight loading!
		 */
		
		ImportData data = DataManager.importData(filenameForImport);
		
		for(Passenger p1:originalPassengerList)
		{
			boolean matchFound = false;
			
			for(Passenger p2:data.passengers)
			{
				if(isPassengerEqual(p1, p2))
				{
					if(p1.getBookedFlights().size() == p2.getBookedFlights().size())
					{
						matchFound = true;
					}
					else fail("Passenger loaded incorrect number of booked flights: " + passToString(p2));
					
					if(p1.getStandbyFlights().size() != p2.getStandbyFlights().size())
					{
						fail("Passenger loaded incorrect number of standby flights: " + passToString(p2));
					}
					
					break;
				}
			}
			
			if(!matchFound)
				fail("Import did not load an expected Passenger:  " + passToString(p1));
		}
	}
	
	@Test
	public void testPassengerAlertLoading() 
	{
		ImportData data = DataManager.importData(filenameForImport);
		
		for(Passenger p1:originalPassengerList)
		{
			boolean matchFound = false;
			
			for(Passenger p2:data.passengers)
			{
				if(isPassengerEqual(p1, p2))
				{
					if(p1.getAlerts().equals(p2.getAlerts()))
					{
						matchFound = true;
					}
					else fail("Mismatch between expected and actual alert lists for a passenger: " + passToString(p2));
					break;
				}
			}
			
			if(!matchFound)
				fail("Import did not load an expected Passenger:  " + passToString(p1));
		}
	}
	
	@Test
	public void testInitialPassengerLoading() 
	{
		ImportData data = DataManager.importData(filenameForImport);
		
		for(Passenger p1:originalPassengerList)
		{
			boolean matchFound = false;
			
			for(Passenger p2:data.passengers)
			{
				if(isPassengerEqual(p1, p2))
				{
					matchFound = true;
					break;
				}
			}
			
			if(!matchFound)
				fail("Import did not load an expected Passenger:  " + passToString(p1));
		}
	}
	
	@Test
	public void testInitialFlightLoading() 
	{
		ImportData data = DataManager.importData(filenameForImport);
		
		if(data.flights.size() != originalFlightList.size()) fail("Error - improper count of flights loaded.");
		
		for(Flight f1:originalFlightList)
		{
			boolean matchFound = false;
			
			for(Flight f2:data.flights)
			{
				if(isFlightEqual(f1, f2))
				{
					matchFound = true;
					break;
				}
			}
			
			if(!matchFound)
				fail("Import did not load an expected Flight:  " + flightToString(f1));
		}
	}
	
	public String passToString(Passenger p)
	{
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append("Passenger(\"");
		strBuilder.append(p.getFirstName());
		strBuilder.append("\", \"");
		strBuilder.append(p.getLastName());
		strBuilder.append("\")");
		
		return strBuilder.toString();
	}
	
	public String flightToString(Flight f)
	{
		StringBuilder strBuilder = new StringBuilder();
		
		strBuilder.append("Flight(\"");
		strBuilder.append(f.getSourceAirport());
		strBuilder.append("\", \"");
		strBuilder.append(f.getDestinationAirport());
		strBuilder.append("\", ");
		strBuilder.append(f.getTakeoffTime());
		strBuilder.append(", ");
		strBuilder.append(f.getLandingTime());
		strBuilder.append(", ");
		strBuilder.append(f.getCapacity());
		strBuilder.append(")");
		
		return strBuilder.toString();
	}
	
	public boolean isPassengerEqual(Passenger expected, Passenger actual)
	{
		if(!expected.getFirstName().equals(actual.getFirstName()))
		{
			return false;
		}
		
		if(!expected.getLastName().equals(actual.getLastName()))
		{
			return false;
		}
		
		return true;
	}
	
	public boolean isFlightEqual(Flight expected, Flight actual)
	{
		if(!expected.getSourceAirport().equals(actual.getSourceAirport()))
		{
			return false;
		}
		
		if(!expected.getDestinationAirport().equals(actual.getDestinationAirport()))
		{
			return false;
		}
		
		if(expected.getTakeoffTime() != actual.getTakeoffTime())
		{
			return false;
		}
		
		if(expected.getLandingTime() != actual.getLandingTime())
		{
			return false;
		}
		
		if(expected.getCapacity() != actual.getCapacity())
		{
			return false;
		}
		
		return true;
	}
	
	public boolean doesFlightMatch(Flight expected, Flight actual)
	{
		if(!expected.getSourceAirport().equals(actual.getSourceAirport()))
		{
			return false;
		}
		
		if(!expected.getDestinationAirport().equals(actual.getDestinationAirport()))
		{
			return false;
		}
		
		if(expected.getTakeoffTime() != actual.getTakeoffTime())
		{
			return false;
		}
		
		if(expected.getLandingTime() != actual.getLandingTime())
		{
			return false;
		}
		
		return true;
	}

}
