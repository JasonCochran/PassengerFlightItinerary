import java.util.ArrayList;


public class GeneralExportMain 
{

	private static ArrayList<Flight> originalFlightList;
	private static ArrayList<Passenger> originalPassengerList;
	
	private static String filenameForExport = "CustomDataTest.txt";
	
	/**
	 * Set up the original version of the Flight list and Passenger lists in this method, 
	 * saving them in the two fields listed above.  You should copy whatever contents you
	 * give this method, line for line, into the corresponding method in
	 * GeneralImportTest.java.
	 */
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
		p5.bookFlight(f3);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		initializeOriginalData();
		
		DataManager.exportData(filenameForExport, originalPassengerList, originalFlightList);
	}

}
