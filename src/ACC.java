import java.util.ArrayList;

public class ACC {
	
	
	public String code;
	public Airport[] Airports= new Airport[1000];
	public ArrayList<Flight> allFlightsForAcc = new ArrayList<>();
	public FlightQueue AccReadyQueue;
	public ArrayList<Flight> AccWaitingQueue;
	
	
	public ACC(String accCode) {
		 this.code=accCode;
	 }
	
	
	public void addAirport(String airportCode) {
		int slot = 0;
		for (int i = 0; i < airportCode.length(); i++) {
			char currentChar = airportCode.charAt(i);
			int asciiValue = currentChar;
			slot+=asciiValue*Math.pow(31,i);
		}
		slot = slot%1000;
		while(Airports[slot]!=null) {
			slot=(slot+1)%1000;
		}
		
		Airport airportToBeAddded = new Airport(airportCode);
		ATC atcOfThatAirport = new ATC(this.code + slot);
		airportToBeAddded.setAtc(atcOfThatAirport);
		Airports[slot] = airportToBeAddded;
	}
	
	public Airport getAirport(String airportCode) {
		int slot = 0;
		for (int i = 0; i < airportCode.length(); i++) {
			char currentChar = airportCode.charAt(i);
			int asciiValue = currentChar;
			slot+=asciiValue*Math.pow(31,i);
		}
		slot = slot%1000;
		while(Airports[slot].code.compareTo(airportCode)!=0) {
			slot=(slot+1)%1000;
		}
		return Airports[slot];
	}
	
	
	public Flight getCurrentFromReadyQueue() {
		try {
			return AccReadyQueue.peek();			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	
}
