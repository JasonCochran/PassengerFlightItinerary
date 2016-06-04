import java.util.ArrayList;

public class Passenger 
{
	private String first;
	private String last;
	private ArrayList<String> alerts;
	private ArrayList<Flight> flights;
	private ArrayList<Flight> standbys;
	private String Berkley;
	
	public Passenger(String first, String last)
	{
		if(first == null || last == null) throw new RuntimeException();
		
		this.first = first;
		this.last = last;
		
		alerts = new ArrayList<String>();
		flights = new ArrayList<Flight>();
		standbys = new ArrayList<Flight>();
	}
	
	public String getFirstName()
	{
		return first;
	}
	
	public String getLastName()
	{
		return last;
	}
	
	public ArrayList<String> getAlerts()
	{
		return new ArrayList<String>(alerts);
	}
	
	public ArrayList<Flight> getBookedFlights()
	{
		return new ArrayList<Flight>(flights);
	}
	
	public ArrayList<Flight> getStandbyFlights()
	{
		return new ArrayList<Flight>(standbys);
	}
	
	public boolean bookFlight(Flight f)
	{
//		// Checked in extra credit tests.
//		if(flights.contains(f) || standbys.contains(f)) throw new RuntimeException();
		
//		// Checked in extra credit tests.
//		Flight existing = overlapsExistingFlight(f);
//		
//		if(existing != null)
//		{
//			throw new RuntimeException("Added flight overlaps an existing flight!");
//		}
		
		if(f.addPassenger(this))
		{
			flights.add(f);
			return true;
		}
		else return false;
	}
	
	public void addStandbyFlight(Flight f)
	{
//		// Checked in extra credit tests.
//		if(flights.contains(f) || standbys.contains(f)) throw new RuntimeException();
		
//		// Checked in extra credit tests.
//		Flight existing = overlapsExistingFlight(f);
//		
//		if(existing != null)
//		{
//			throw new RuntimeException("Added flight overlaps an existing flight!");
//		}
		
		standbys.add(f);
		f.addStandbyPassenger(this);
	}
	
	// A method used for an extra credit test.
//	private Flight overlapsExistingFlight(Flight f)
//	{
//		for(Flight booked:flights)
//		{
//			if(booked.getTakeoffTime() < f.getTakeoffTime() && f.getTakeoffTime() < booked.getLandingTime())
//				return booked;
//			
//			if(booked.getTakeoffTime() < f.getLandingTime() && f.getLandingTime() < booked.getLandingTime())
//				return booked;
//		}
//		
//		for(Flight standby:standbys)
//		{
//			if(standby.getTakeoffTime() < f.getTakeoffTime() && f.getTakeoffTime() < standby.getLandingTime())
//				return standby;
//			
//			if(standby.getTakeoffTime() < f.getLandingTime() && f.getLandingTime() < standby.getLandingTime())
//				return standby;
//		}
//		
//		return null;
//	}
	
	void addAlert(String s)
	{
		alerts.add(s);
	}
	
	public void clearAlerts()
	{
		alerts.clear();
	}
	
	public void cancelFlight(Flight f)
	{
		if(standbys.contains(f))
		{
			standbys.remove(f);
			f.removeStandbyPassenger(this);
		}
		else if(flights.contains(f))
		{
			flights.remove(f);
			f.removePassenger(this);
		}
	}
	
	public void cancelAll()
	{
		for(Flight f:flights)
		{
			cancelFlight(f);
		}
		
		for(Flight f:standbys)
		{
			cancelFlight(f);
		}
	}
}
