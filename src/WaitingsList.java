import java.util.ArrayList;
import java.util.Iterator;

public class WaitingsList {
	private ArrayList<Flight> waitingsList = new ArrayList<>();
	
	
//	public WaitingsList(ArrayList<Flight> wt) {
//		this .waitingsList = wt;
//	}
//	
	public int getSize() {
		return this.waitingsList.size();
	}
	
	public ArrayList<Flight> getElements(){
		return this.waitingsList;
	}
	
	
	public void addToQueue(Flight f) {
		this.waitingsList.add(f);
	}
	
	
	public ArrayList<Flight> extractReadyFlights(){
		ArrayList<Flight> readyFlights = new ArrayList<>();
		ArrayList<Flight> remainersFromWaitingList = new ArrayList<>();
		if(this.waitingsList.size()>0) {
			for (Flight flight : waitingsList) {
				if(flight.getCurrentOperation().remainingTime==0) {
					readyFlights.add(flight);
				}
				else {
					remainersFromWaitingList.add(flight);
				}
			}
		
			this.waitingsList = remainersFromWaitingList;
		}
		
		return readyFlights;
	}
}
