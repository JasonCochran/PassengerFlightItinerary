import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RandomItinerary 
{
	private static String[] codes = {"GNV", "BTR", "MCO", "MIA", "ATL", "IAH", "LAX", "JFK", "LGA", "ORD", "BOS", "ANC", "DEN", "SLC", "SFO", "IAD", "SEA", "OKC"};

	// Used internally.
	private static int getRandomInt(int min, int max)
	{
		int x;  // We're going to divide integers.
		// Don't allow y == 0!

		x = (int) (Math.random() * (max - min) + min);
		
		return x;
	}
	
	/*
	 * Gets a randomly-generated itinerary - a array of sequential airport code pairings.
	 * @return
	 */
	public static String[] get()
	{
		ArrayList<String> codeList = new ArrayList<String>(Arrays.asList(codes));
		
		Collections.shuffle(codeList);
		
		int count = getRandomInt(1, 6);
		
		String[] itinerary = new String[count];
		
		for(int i=0; i < count; i++)
		{
			itinerary[i] = codeList.get(i) + "-" + codeList.get(i+1);
		}
		
		return itinerary;
	}
}
