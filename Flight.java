import java.util.ArrayList;

public class Flight 
{
	private String srcCode;
	private String dstCode;
	
	private int takeoff;
	private int landing;
	
	private int capacity;
	
	private ArrayList<Passenger> booked;
	private ArrayList<Passenger> standbys;
	
	public Flight(String src, String dest, int takeoffTime, int landingTime, int capacity)
	{
		if(src == null || dest == null) throw new RuntimeException();
		if(takeoffTime > landingTime) throw new RuntimeException();
		
//		// This part was extra credit.
//		if(takeoffTime < 0) throw new RuntimeException();
//		if(landingTime > 2400) throw new RuntimeException();
//		if(takeoffTime % 100 >= 60) throw new RuntimeException();
//		if(landingTime % 100 >= 60) throw new RuntimeException();
		
		if(capacity < 0) throw new RuntimeException();
		
//		// This part was extra credit.
//		if(src.equals(dest)) throw new RuntimeException();
		
		this.srcCode = src;
		this.dstCode = dest;
		this.takeoff = takeoffTime;
		this.landing = landingTime;
		this.capacity = capacity;
		
		booked = new ArrayList<Passenger>(capacity);
		standbys = new ArrayList<Passenger>();
	}
	
	public String getSourceAirport()
	{
		return srcCode;
	}
	
	public String getDestinationAirport()
	{
		return dstCode;
	}
	
	public int getCapacity()
	{
		return capacity;
	}
	
	public int getTakeoffTime()
	{
		return takeoff;
	}
	
	public int getLandingTime()
	{
		return landing;
	}
	
	public ArrayList<Passenger> getBookedPassengers()
	{
		return new ArrayList<Passenger>(booked);
	}
	
	public ArrayList<Passenger> getStandbyPassengers()
	{
		return new ArrayList<Passenger>(standbys);
	}
	
	public boolean addPassenger(Passenger p)
	{
		if(p == null) throw new RuntimeException();
		
//		// Checked in extra credit tests.
//		if(booked.contains(p)) throw new RuntimeException();
//		if(standbys.contains(p)) throw new RuntimeException();
		
		if(booked.size() < capacity)
		{
			booked.add(p);
			return true;
		}
		else return false;
	}
	
	public void addStandbyPassenger(Passenger p)
	{
		if(p == null) throw new RuntimeException();
		
//		// Checked in extra credit tests.
//		if(standbys.contains(p)) throw new RuntimeException();
//		if(booked.contains(p)) throw new RuntimeException();
		
		standbys.add(p);
	}
	
	public void removePassenger(Passenger p)
	{
		booked.remove(p);
	}
	
	public void removeStandbyPassenger(Passenger p)
	{
		standbys.remove(p);
	}
	
	public void cancel()
	{
		while(booked.size() > 0)
		{
			Passenger p = booked.get(0);
			p.addAlert("The " + takeoff + " flight from " + srcCode + " to " + dstCode + " has been cancelled.");
			p.cancelFlight(this);
		}
		
		while(standbys.size() > 0)
		{
			Passenger p = standbys.get(0);
			p.addAlert("Your tentative (standby) " + takeoff + " flight from " + srcCode + " to " + dstCode + " has been cancelled.");
			p.cancelFlight(this);
		}
	}
	
	public int promotePassengers()
	{
		int emptySpace = capacity - booked.size();
		if(emptySpace < 0) throw new IllegalStateException();
		
		int count = 0;
		
		while(emptySpace > 0 && standbys.size() > 0)
		{
			Passenger p = standbys.get(0);
			p.cancelFlight(this);
			p.bookFlight(this);
			
			p.addAlert("You now have guaranteed seating on the " + takeoff + " flight from " + srcCode + " to " + dstCode + ".");
			emptySpace--;
			count++;
		}
		
		return count;
	}

	
	@Override
	public String toString() {
		return srcCode + ", " + dstCode + ", " + takeoff + ", " + landing;
	}

	
}
