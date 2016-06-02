import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManager {	
	public static void exportData(String filename, ArrayList<Passenger> passengers, ArrayList<Flight> flights) {
		//TODO need error catching
		PrintStream outFile;
		try {
			outFile = new PrintStream(filename);
			
			//Flight Printer
			outFile.println("#flightCount " + flights.size() );
			for(int i = 0; i < flights.size(); i++) {
				
				outFile.println("#newFlight");
				outFile.println( flights.get(i).getSourceAirport() + " , " + flights.get(i).getDestinationAirport() + " , " + flights.get(i).getTakeoffTime() + " , " + flights.get(i).getLandingTime() );
				outFile.println( flights.get(i).getCapacity() );
			}

			//Passenger Printer
			outFile.println("#passCount " + passengers.size() );
			for(int j = 0; j < passengers.size(); j++) {
				
				outFile.println("#newPass");
				outFile.println( passengers.get(j).getFirstName() + " , " + passengers.get(j).getLastName() );
				//Alerts
				outFile.println( passengers.get(j).getAlerts().size() );
				for(int i = 0; i < passengers.get(j).getAlerts().size(); i++ ) {
					outFile.println( passengers.get(j).getAlerts().get(i) );
				}
				//BookedFlights
				outFile.println( passengers.get(j).getBookedFlights().size() );
				for(int i = 0; i < passengers.get(j).getBookedFlights().size(); i++ ) {
					outFile.println( passengers.get(j).getBookedFlights().get(i).toString() );
				}
				//StandbyFlights
				outFile.println( passengers.get(j).getStandbyFlights().size() );
				for(int i = 0; i < passengers.get(j).getStandbyFlights().size(); i++ ) {
					outFile.println( passengers.get(j).getStandbyFlights().get(i).toString() );
				}
			}
			outFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	public static ImportData importData(String filename) {
		//This function imports previously exported data from an existing file.
		//This file must follow the exact specifications given above.
		//All represented data is returned through a single instance of ImportData – an object designed to
		//		store the two ArrayLists that would have been used to originally export the data.
		
		try {
			Scanner inFile = new Scanner(new File(filename) );
			
			ArrayList<Passenger> passengers = new ArrayList<Passenger>();
			ArrayList<Flight> flights = new ArrayList<Flight>();
			
			int iterationCount;
			//Flight data importer
			//#flightCount #
			//#newFlight
			//MCO , MIA , 800 , 930
			//8
			//Gets iteration count and skips flightCount
			inFile.next();
			iterationCount = Integer.parseInt( inFile.next() );
			
			for(int i = 0; i <  iterationCount; i++) {
				String tempDest;
				String tempOrig;
				int tempDep;
				int tempArr;
				int tempCap;
				
				inFile.next();
				tempOrig = inFile.next();
				inFile.next();
				tempDest = inFile.next();
				inFile.next();
				tempDep = Integer.parseInt(inFile.next());
				inFile.next();
				tempArr = Integer.parseInt( inFile.next() );
				tempCap = Integer.parseInt( inFile.next() );
				
				Flight flight = new Flight(tempOrig, tempDest, tempDep, tempArr, tempCap);
				flights.add(flight);
			}
			
			//Passenger data importer
			//passCount #
			//#newPass
			//Joshua , Horton
			//1
			//The 7:30 flight from BTR to GNV has been cancelled!
			//2
			//MCO , MIA , 800 , 930
			//MIA , ATL , 1100 , 1400
			//0
			inFile.next();
			iterationCount = Integer.parseInt( inFile.next() );
			
			for(int i = 0; i <  iterationCount; i++) {
				String tempFirst;
				String tempLast;

				inFile.next();
				tempFirst = inFile.next();
				inFile.next();
				tempLast = inFile.next();
				Passenger passenger = new Passenger(tempFirst, tempLast);
				
				//Alerts
				int alertCount = Integer.parseInt( inFile.next() );
				for(int j = 0; j < alertCount; j++) {
					String alert = inFile.nextLine();
					passenger.addAlert(alert);
				}
				
				//Booked flights
				int flightCount = Integer.parseInt( inFile.next() );
				for(int j = 0; j < flightCount; j++) {
					String tempOrig = inFile.next();
					inFile.next();
					String tempDest = inFile.next();
					inFile.next();
					int tempArr = Integer.parseInt( inFile.next() );
					inFile.next();
					int tempDep = Integer.parseInt( inFile.next() );
					Flight myFLight = FlightSearcher(tempOrig, tempDest, tempArr, tempDep, flights);
					passenger.bookFlight(myFLight);
				}
				
				//Standby Flights
				flightCount = Integer.parseInt( inFile.next() );
				for(int j = 0; j < flightCount; j++) {
					String tempOrig = inFile.next();
					inFile.next();
					String tempDest = inFile.next();
					inFile.next();
					int tempArr = Integer.parseInt( inFile.next() );
					inFile.next();
					int tempDep = Integer.parseInt( inFile.next() );
					Flight myFLight = FlightSearcher(tempOrig, tempDest, tempArr, tempDep, flights);
					passenger.addStandbyFlight(myFLight);
				}
			}
			
			
			
			ImportData importedData = new ImportData(passengers, flights);

			inFile.close();
			return importedData; //Should this be an object return?
			
		} catch (NumberFormatException e) {
			System.out.println("Yo input is wrong dude D; ");
			return null;
		} catch (FileNotFoundException e) {
			System.out.println("Filez not founded :(");
			return null;
		}
	}

	public static Flight FlightSearcher(String Orig, String Dest, int arr, int dep, ArrayList<Flight> flights ) {
		
		Flight myFlight = null; 
		for(int i = 0; i < flights.size(); i++) {
			if(flights.get(i).getSourceAirport().equals(Orig) && flights.get(i).getDestinationAirport().equals(Dest) && flights.get(i).getLandingTime() == arr && flights.get(i).getTakeoffTime() == dep) {
				myFlight = flights.get(i);
			}
		}
		
		return myFlight;
	}
}
